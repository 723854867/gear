package org.gear.gateway.auth.bean.model;

import java.io.Serializable;

import org.gear.auth.bean.Modular;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModularChainNode implements Serializable {

	private static final long serialVersionUID = -6598864878073309698L;

	private int id;
	private int left;
	private int right;
	private int parent;
	private String name;
	
	public ModularChainNode(Modular modular) {
		this.id = modular.getId();
		this.left = modular.getLeft();
		this.name = modular.getName();
		this.right = modular.getRight();
	}
}
