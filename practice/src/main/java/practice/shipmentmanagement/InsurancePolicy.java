package practice.shipmentmanagement;

public class InsurancePolicy {
    private final String policyNumber;
    private double insuredAmount;

    public InsurancePolicy(String policyNumber,double insuredAmount){
        this.policyNumber=policyNumber;
        if(insuredAmount<0){
            throw new IllegalArgumentException("insured Amount is invalid");
        }
        this.insuredAmount=insuredAmount;
    }

    public String getPolicyNumber(){
        return policyNumber;
    }

    public double getInsuredAmount(){
        return insuredAmount;
    }



}
