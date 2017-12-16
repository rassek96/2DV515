package a4;

import java.util.LinkedHashMap;

import core.Attribute;
import core.Classifier;
import core.Dataset;
import core.Instance;
import core.Result;

public class NaiveBayes implements Classifier{
	private LinkedHashMap<String, Integer> attrFreq;
	private int goodCount;
	private int badCount;
	
	@Override
	public void train(Dataset train) {
		//Reset
		attrFreq = new LinkedHashMap<>();
		goodCount = 0;
		badCount = 0;
		//Calculate frequency for each attribute
		//For every attribute for each instance
		for(int i = 0; i < train.noInstances(); i++) {
			Instance inst = train.getInstance(i);
			for(int a = 0; a < inst.noAttributes(); a++) {
				Attribute attr = inst.getAttribute(a);
				String key = train.getAttributeName(a) + " " + attr.value();
				//Count frequency of attributes
				if(attrFreq.containsKey(key)) {
					attrFreq.put(key, attrFreq.get(key) + 1);
				}
				else {
					attrFreq.put(key, 1);
				}
				//Count good/bad playerskill
				if(key.equals("PlayerSkill good")) {
					goodCount++;
				}
				else if(key.equals("PlayerSkill bad")) {
					badCount++;
				}
			}
		}
	}
	@Override
	public Result classify(Instance inst) {
		double[] goodCalc = new double[3];
		double[] badCalc = new double[3];
		//Calculate probability
		for(int i = 0; i < inst.noAttributes() - 1; i++) {
			String key = inst.getAttributeName(i) + " " + inst.getAttributeArrayNominal()[i];
			goodCalc[i] = (double)attrFreq.get(key) / (double)goodCount;
			badCalc[i] = (double)attrFreq.get(key) / (double)badCount;
		}
		double goodProb = (goodCalc[0] * goodCalc[1] * goodCalc[2]);
		double badProb = (badCalc[0] * badCalc[1] * badCalc[2]);
		String highestProb;
		if(goodProb > badProb) {
			highestProb = "good";
		}
		else {
			highestProb = "bad";
		}
		return new Result(highestProb);
	}
	
	@Override
	public String toString() {
		return "Naive Bayes for FIFA skill";
	}
}
