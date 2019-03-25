package cn.nju.edu.model;

public class Project {
	private final int projectId;
	private final String projectTitle;
	private final String projectContent;
	private final int ownerId;
	private final String ownerEmail;
	private final String ownerName;
	
	public Project(int projectId, String projectTitle, String projectContent, int ownerId, String ownerEmail,
			String ownerName) {
		super();
		this.projectId = projectId;
		this.projectTitle = projectTitle;
		this.projectContent = projectContent;
		this.ownerId = ownerId;
		this.ownerEmail = ownerEmail;
		this.ownerName = ownerName;
	}
	
	public int getProjectId() {
		return projectId;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public String getProjectContent() {
		return projectContent;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public String getOwnerName() {
		return ownerName;
	}
}
