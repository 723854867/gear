package org.gear.gateway.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gear.auth.bean.Modular;
import org.gear.gateway.auth.bean.model.ModularChainNode;
import org.gear.gateway.auth.bean.model.ModularTreeNode;
import org.jupiter.bean.model.MultiListMap;
import org.jupiter.util.lang.CollectionUtil;

public class AuthUtil {

	public static final List<ModularTreeNode> tree(List<Modular> modulars) {
		List<ModularTreeNode> roots = new ArrayList<ModularTreeNode>();
		if (CollectionUtil.isEmpty(modulars))
			return roots;
		Collections.sort(modulars, (o1, o2) -> o1.getLayer() - o2.getLayer());
		int minLayer = modulars.get(0).getLayer();
		MultiListMap<Integer, Modular> map = new MultiListMap<Integer, Modular>();
		modulars.forEach(modular -> map.add(modular.getLayer(), modular));
		MultiListMap<String, ModularTreeNode> tree = new MultiListMap<String, ModularTreeNode>();
		int layer = minLayer;
		while (true) {
			List<Modular> l = map.get(layer);
			if (null == l)
				break;
			MultiListMap<String, ModularTreeNode> temp = new MultiListMap<String, ModularTreeNode>();
			for (Modular modular : l) {
				ModularTreeNode node = new ModularTreeNode(modular);
				temp.add(modular.getTrunk(), node);
				List<ModularTreeNode> parents = tree.get(modular.getTrunk());
				if (!CollectionUtil.isEmpty(parents)) {
					for (ModularTreeNode parent : parents) {
						if (modular.getLeft() > parent.getLeft() && modular.getRight() < parent.getRight())
							parent.addChild(node);
					}
				}
			}
			tree = temp;
			if (layer == minLayer) 
				tree.values().forEach(nodes -> {nodes.forEach(node -> roots.add(node));});
			layer++;
		}
		return roots;
	}
	
	public static final List<ModularChainNode> chain(List<Modular> modulars){
		List<ModularChainNode> list = new ArrayList<ModularChainNode>();
		if (CollectionUtil.isEmpty(modulars))
			return list;
		Collections.sort(modulars, (o1, o2) -> o1.getLayer() - o2.getLayer());
		int minLayer = modulars.get(0).getLayer();
		MultiListMap<Integer, Modular> map = new MultiListMap<Integer, Modular>();
		modulars.forEach(modular -> map.add(modular.getLayer(), modular));
		MultiListMap<String, ModularChainNode> tree = new MultiListMap<String, ModularChainNode>();
		int layer = minLayer;
		while (true) {
			List<Modular> l = map.get(layer);
			if (null == l)
				break;
			MultiListMap<String, ModularChainNode> temp = new MultiListMap<String, ModularChainNode>();
			for (Modular modular : l) {
				ModularChainNode node = new ModularChainNode(modular);
				temp.add(modular.getTrunk(), node);
				List<ModularChainNode> parents = tree.get(modular.getTrunk());
				if (!CollectionUtil.isEmpty(parents)) {
					for (ModularChainNode parent : parents) {
						if (modular.getLeft() > parent.getLeft() && modular.getRight() < parent.getRight())
							node.setParent(parent.getId());
					}
				}
			}
			tree = temp;
			temp.values().forEach(nodes -> {nodes.forEach(node -> list.add(node));});
			layer++;
		}
		return list;
	}
}
