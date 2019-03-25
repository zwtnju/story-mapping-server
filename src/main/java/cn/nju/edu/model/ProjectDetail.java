package cn.nju.edu.model;

import java.util.List;

public class ProjectDetail extends Project {
	private List<Contributor> contributors;
	private String projectCards;

	public ProjectDetail(int projectId, String projectTitle, String projectContent, int ownerId, String ownerEmail,
			String ownerName, List<Contributor> contributors, String projectCards) {
		super(projectId, projectTitle, projectContent, ownerId, ownerEmail, ownerName);
		this.projectCards = projectCards;
		this.contributors = contributors;
	}

	public List<Contributor> getContributors() {
		return contributors;
	}

	public String getProjectCards() {
		return projectCards;
	}
	
}
