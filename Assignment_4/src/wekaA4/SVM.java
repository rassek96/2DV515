package wekaA4;

import core.ARFFreader;
import core.Dataset;
import core.Instance;
import core.Result;
import libsvm.*;

public class SVM {

	private svm_model model;
	private Dataset data;
	
	public SVM(String filename) {
		ARFFreader arff = new ARFFreader();
		arff.readFile(filename);
		this.data = arff.getData();
		this.train();
		this.test();
	}
	
	private void train() {
		int n = data.noInstances();
		svm_problem prob = new svm_problem();
		prob.y = new double[n];
		prob.l = n;
		prob.x = new svm_node[n][data.noAttributes() - 1];
		for(int i = 0; i < data.noInstances(); i++) {
			Instance inst = data.getInstance(i);
			double[] vals = inst.getAttributeArrayNumerical();
			prob.x[i] = new svm_node[data.noAttributes() - 1];
			for(int a = 0; a < data.noAttributes() - 1; a++) {
				svm_node node = new svm_node();
				node.index = a;
				node.value = vals[a];
				prob.x[i][a] = node;
			}
			prob.y[i] = inst.getClassAttribute().numericalValue();
		}
		
		svm_parameter param = new svm_parameter();
		param.probability = 1;
		param.gamma = 0.5;
		param.nu = 0.5;
		param.C = 100;
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.RBF;
		param.cache_size = 20000;
		param.eps = 0.001;
		
		model = svm.svm_train(prob, param);
	}
	private Result classifyInstance(Instance inst) { 
		double[] vals = inst.getAttributeArrayNumerical();
		int no_classes = data.noClassValues();
		svm_node[] nodes = new svm_node[vals.length];
		for(int a = 0; a < vals.length; a++) {
			svm_node node = new svm_node();
			node.index = a;
			node.value = vals[a];
			nodes[a] = node;
		}
		int[] labels = new int[no_classes];
		svm.svm_get_labels(model, labels);
		double[] prob_estimates = new double[no_classes];
		double cVal = svm.svm_predict_probability(model, nodes, prob_estimates);
		return new Result(cVal);
	}
	
	private void test() {
		System.out.println("===========================");
		System.out.println("========= Lib SVM =========");
        int n = data.noInstances();
        int no_correct = 0;
        for (int r = 0; r < n; r++) {
            Instance inst = data.getInstance(r);
            Result res = classifyInstance(inst);
            if (res.isNominal()) {
                if (inst.getClassAttribute().nominalValue().equalsIgnoreCase(res.nominalValue())) {
                    no_correct++;
                }
            }
            else {
                if (inst.getClassAttribute().numericalValue() == res.numericalValue()) {
                    no_correct++;
                }
            }
        }
        double perc = (double)no_correct / ((double)n) * 100.0;
        System.out.println("Evaluation (whole dataset): " + String.format( "%.2f", perc) + "%");
	}
}
