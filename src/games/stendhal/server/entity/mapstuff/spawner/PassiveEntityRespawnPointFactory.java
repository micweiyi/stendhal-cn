/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.mapstuff.spawner;

import java.util.Arrays;

import org.apache.log4j.Logger;

import marauroa.common.game.IRPZone.ID;

/**
 * creates a PassiveEntityRespawnPoint.
 */
public class PassiveEntityRespawnPointFactory {
	private static Logger logger = Logger
			.getLogger(PassiveEntityRespawnPointFactory.class);

	/**
	 * creates a PassiveEntityRespawnPoint.
	 *
	 * @param clazz
	 *            class
	 * @param type
	 *            type
	 * @param id
	 *            zone id
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @return PassiveEntityRespawnPoint or null in case some error occurred
	 */
	public static PassiveEntityRespawnPoint create(final String clazz,
			final int type, final ID id, final int x, final int y) {
		PassiveEntityRespawnPoint passiveEntityrespawnPoint = null;

		if (clazz.contains("herb")) {
			passiveEntityrespawnPoint = createHerb(type);

		} else if (clazz.contains("corn")) {
			passiveEntityrespawnPoint = createGrain(type);

		} else if (clazz.contains("mushroom")) {
			passiveEntityrespawnPoint = createMushroom(type);

		} else if (clazz.contains("resources")) {
			passiveEntityrespawnPoint = createResource(type);

		} else if (clazz.contains("sheepfood")) {
			passiveEntityrespawnPoint = new SheepFood();

		} else if (clazz.contains("vegetable")) {
			passiveEntityrespawnPoint = createVegetable(type);

		} else if (clazz.contains("jewelry")) {
			passiveEntityrespawnPoint = createJewelry(type);

		} else if (clazz.contains("sign")) {
			/*
			 * Ignore signs. The way to go is XML.
			 */
			return null;

		} else if (clazz.contains("fruits")) {
			passiveEntityrespawnPoint = createFruit(type);

		} else if (clazz.contains("meat_and_fish")) {
			passiveEntityrespawnPoint = createMeatAndFish(type);

		} else if (clazz.contains("dairy")) {
			passiveEntityrespawnPoint = createDairy(type);
		}

		if (passiveEntityrespawnPoint == null) {
			logger.error("Unknown Entity (class/type: " + clazz + ":" + type
					+ ") at (" + x + "," + y + ") of " + id + " found");
			return null;
		}

		return passiveEntityrespawnPoint;
	}

	private static PassiveEntityRespawnPoint createDairy(final int type) {
		PassiveEntityRespawnPoint passiveEntityrespawnPoint;
		switch (type) {
		case 0:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("egg", 900);
			passiveEntityrespawnPoint
					.setDescription("假如你是只母鸡，偶尔也会在这儿下蛋。");
			break;
		default:
			passiveEntityrespawnPoint = null;
			break;
		}
		return passiveEntityrespawnPoint;
	}

	private static PassiveEntityRespawnPoint createMeatAndFish(final int type) {
		PassiveEntityRespawnPoint passiveEntityrespawnPoint;
		switch (type) {
		case 0:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("肉", 100);
			passiveEntityrespawnPoint.setDescription("这是一些动物吃剩的食物，像是肉。");
			break;
		case 1:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("ham", 100);
			passiveEntityrespawnPoint.setDescription("这是一些动物吃剩的食物，像是ham.");
			break;
		case 2:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("鸡腿", 100);
			passiveEntityrespawnPoint.setDescription("这是一些动物吃剩的食物，像是鸡腿?");
			break;
		case 3:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("roach", 100);
			passiveEntityrespawnPoint.setDescription("从鱼鳞的光泽看，像是roach。");
			break;

		case 4:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("char", 100);
			passiveEntityrespawnPoint.setDescription("从这红色的鱼鳞看，像是char.");
			break;
		default:
			passiveEntityrespawnPoint = null;
			break;
		}
		return passiveEntityrespawnPoint;
	}

	private static PassiveEntityRespawnPoint createFruit(final int type) {
		PassiveEntityRespawnPoint passiveEntityrespawnPoint;
		switch (type) {
		case 0:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("coconut", 800);
			passiveEntityrespawnPoint
					.setDescription("这里应该会有椰子落下。");
			break;
		case 1:
			passiveEntityrespawnPoint = new VegetableGrower("tomato");
			passiveEntityrespawnPoint
					.setDescription("这是一株西红柿。");
			break;
		case 2:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("pineapple", 1200);
			break;
		case 3:
			passiveEntityrespawnPoint = new VegetableGrower("watermelon");
			passiveEntityrespawnPoint
					.setDescription("这是一株西瓜藤.");
			break;
		case 4:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("banana", 1000);
			passiveEntityrespawnPoint
					.setDescription("这棵树上的香蕉快要掉下来。");
			break;
		case 5:
			passiveEntityrespawnPoint = new VegetableGrower("grapes", "已经没有葡萄剩下了。");
			passiveEntityrespawnPoint
					.setDescription("一棵葡萄藤。");
			break;
		case 6:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("pear", 500);
			passiveEntityrespawnPoint
					.setDescription("树上的梨子快要掉下了。");
			break;
		case 7:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("pomegranate", 800);
			passiveEntityrespawnPoint
					.setDescription("树上的石榴都裂口了。");
			break;
		case 8:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("olive", 1200);
			passiveEntityrespawnPoint
					.setDescription("树上的橄榄好像要掉了。");
			break;
		case 9:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("cherry", 1000);
			passiveEntityrespawnPoint
					.setDescription("红色的樱桃挂满枝头。");
			break;
		default:
			passiveEntityrespawnPoint = null;
			break;
		}
		return passiveEntityrespawnPoint;
	}

	private static PassiveEntityRespawnPoint createJewelry(final int type) {
		PassiveEntityRespawnPoint passiveEntityrespawnPoint;
		switch (type) {
		case 0:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("carbuncle", 6000);
			passiveEntityrespawnPoint.setDescription("这里的矿脉有一些红色水晶的纹路。");
			break;
		case 1:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("sapphire", 6000);
			passiveEntityrespawnPoint.setDescription("这里的矿脉显示有sapphire存在的迹象。");
			break;
		case 2:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("emerald", 6000);
			passiveEntityrespawnPoint.setDescription("这些石头里有emerald存在的迹象。");
			break;
		default:
			passiveEntityrespawnPoint = null;
			break;
		}
		return passiveEntityrespawnPoint;
	}

	private static PassiveEntityRespawnPoint createVegetable(final int type) {
		PassiveEntityRespawnPoint passiveEntityrespawnPoint;
		switch (type) {
		case 0:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("apple", 500);
			passiveEntityrespawnPoint.setDescription("树上的苹果快要落下来了。");
			break;
		case 1:
			passiveEntityrespawnPoint = new VegetableGrower("carrot");
			passiveEntityrespawnPoint.put("menu", "Pick|Use");
			break;
		case 2:
			passiveEntityrespawnPoint = new VegetableGrower("salad");
			break;
		case 3:
			passiveEntityrespawnPoint = new VegetableGrower("broccoli");
			break;
		case 4:
			passiveEntityrespawnPoint = new VegetableGrower("cauliflower");
			break;
		case 5:
			passiveEntityrespawnPoint = new VegetableGrower("chinese cabbage");
			break;
		case 6:
			passiveEntityrespawnPoint = new VegetableGrower("leek");
			break;
		case 7:
			passiveEntityrespawnPoint = new VegetableGrower("onion");
			break;
		case 8:
			passiveEntityrespawnPoint = new VegetableGrower("courgette");
			break;
		case 9:
			passiveEntityrespawnPoint = new VegetableGrower("spinach");
			break;
		case 10:
			passiveEntityrespawnPoint = new VegetableGrower("collard");
			break;
		case 11:
			passiveEntityrespawnPoint = new VegetableGrower("garlic");
			break;
		case 12:
			passiveEntityrespawnPoint = new VegetableGrower("artichoke");
			break;
		default:
			passiveEntityrespawnPoint = null;
			break;
		}
		return passiveEntityrespawnPoint;
	}

	private static PassiveEntityRespawnPoint createResource(final int type) {
		PassiveEntityRespawnPoint passiveEntityrespawnPoint;
		switch (type) {
		case 0:
			passiveEntityrespawnPoint = new VegetableGrower("wood", "虽然没有要捡的东西，但这些木头还是可以用用。");
			passiveEntityrespawnPoint.put("menu", "Pick|Use");
			passiveEntityrespawnPoint.setDescription("一根圆木倒在地下。");
			break;
		case 1:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("iron ore", 3000);
			passiveEntityrespawnPoint.setDescription("你发现这些矿脉中可能存在铁矿。");
			break;

		case 2:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("gold bar", 9000);
			passiveEntityrespawnPoint.setDescription("这里金光闪烁。");
			break;
		case 3:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("mithril bar", 16000);
			passiveEntityrespawnPoint.setDescription("这里银光闪闪。");
			break;
		case 4:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("gold nugget", 6000);
			passiveEntityrespawnPoint.setDescription("这是一些很小的薄金片");
			break;
		case 5:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("mithril nugget", 12000);
			passiveEntityrespawnPoint.setDescription("我是一小块mithril nugget矿。");
			break;
		default:
			passiveEntityrespawnPoint = null;
			break;

		}
		return passiveEntityrespawnPoint;
	}

	private static PassiveEntityRespawnPoint createMushroom(final int type) {
		PassiveEntityRespawnPoint passiveEntityrespawnPoint;
		switch (type) {
		case 0:
			passiveEntityrespawnPoint = new VegetableGrower("button mushroom");
			passiveEntityrespawnPoint.setDescription("这里长着些小小的button mushroom。");
			break;
		case 1:
			passiveEntityrespawnPoint = new VegetableGrower("porcini");
			passiveEntityrespawnPoint.setDescription("这里长着一些小小的porcini。");
			break;
		case 2:
			passiveEntityrespawnPoint = new VegetableGrower("toadstool");
			passiveEntityrespawnPoint.setDescription("一些toadstool长在这块土地上。");
			break;
		default:
			passiveEntityrespawnPoint = null;
			break;
		}
		return passiveEntityrespawnPoint;
	}

	private static PassiveEntityRespawnPoint createHerb(final int type) {
		PassiveEntityRespawnPoint passiveEntityrespawnPoint;
		switch (type) {
		case 0:
			passiveEntityrespawnPoint = new VegetableGrower("arandula");
			break;
		case 1:
			passiveEntityrespawnPoint = new VegetableGrower("kekik");
			break;
		case 2:
			passiveEntityrespawnPoint = new VegetableGrower("sclaria");
			break;
		case 3:
			passiveEntityrespawnPoint = new VegetableGrower("mandragora");
			break;
		case 4:
			passiveEntityrespawnPoint = new PassiveEntityRespawnPoint("reindeer moss", 300);
			passiveEntityrespawnPoint
					.setDescription("这里是reindeer moss生长的地方。");
			break;
		default:
			passiveEntityrespawnPoint = null;
			break;
		}
		return passiveEntityrespawnPoint;
	}

	private static PassiveEntityRespawnPoint createGrain(final int type) {
		PassiveEntityRespawnPoint passiveEntityrespawnPoint;
		switch (type) {
		case 0:
			passiveEntityrespawnPoint = new GrainField("grain", Arrays.asList("镰刀", "旧的大镰刀", "黑镰刀"));
			break;

		case 1:
			passiveEntityrespawnPoint = new GrainField("sugar cane", Arrays.asList("镰刀", "旧的大镰刀", "黑镰刀"));
			break;
		default:
			passiveEntityrespawnPoint = null;
			break;
		}
		return passiveEntityrespawnPoint;
	}
}
