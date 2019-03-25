package cn.nju.edu.model;	

public class Contributor {
	private final String contributorId;
	private final String contributorEmail;
	private final String contributorName;
	
    public Contributor(String contributorId, String contributorEmail, String contributorName) {
        this.contributorId = contributorId;
        this.contributorEmail = contributorEmail;
        this.contributorName = contributorName;
    }

	public String getContributorId() {
		return contributorId;
	}

	public String getContributorEmail() {
		return contributorEmail;
	}

	public String getContributorName() {
		return contributorName;
	}

}
