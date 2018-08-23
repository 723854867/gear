package org.gear.gateway.auth.bean.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gear.auth.bean.Modular;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModularTreeNode implements Serializable {

	private static final long serialVersionUID = 5710036340799380574L;

	private int id;
	private int left;
	private int right;
	private String name;
	private List<ModularTreeNode> children;
	
	public ModularTreeNode(Modular modular) {
		this.id = modular.getId();
		this.left = modular.getLeft();
		this.name = modular.getName();
		this.right = modular.getRight();
		this.children = new ArrayList<ModularTreeNode>();
	}
	
	public void addChild(ModularTreeNode node) {
		this.children.add(node);
	}
}
