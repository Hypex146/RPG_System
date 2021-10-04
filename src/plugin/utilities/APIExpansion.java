package plugin.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class APIExpansion {
	
	public static List<Entity> getNearEntities(Entity entity, List<EntityType> entityType, int radius){
		List<Entity> targets = new ArrayList<Entity>();
		//TreeMap<Double, Entity> tree = new TreeMap<Double, Entity>();
		for (Entity possibleTarget : entity.getNearbyEntities(radius, radius, radius)) {
			if (!entityType.contains(possibleTarget.getType())) {continue;}
			targets.add(possibleTarget);
		}
		return targets;
	}
	
	public static List<Entity> getNearEntities(Entity entity, List<EntityType> entityType, int radius, int maxCount){
		List<Entity> targets = new ArrayList<Entity>();
		TreeMap<Double, Entity> possibleTargets = new TreeMap<Double, Entity>();
		for (Entity possibleTarget : entity.getNearbyEntities(radius, radius, radius)) {
			if (!entityType.contains(possibleTarget.getType())) {continue;}
			possibleTargets.put(entity.getLocation().distance(possibleTarget.getLocation()), possibleTarget);
		}
		for (int i=0; i<possibleTargets.size(); i++) {
			double firstKey = possibleTargets.firstKey();
			Entity target = possibleTargets.get(firstKey);
			possibleTargets.remove(firstKey);
			targets.add(target);
			if (i >= maxCount) {break;}
		}
		return targets;
	}
	
	public static List<LivingEntity> getLookingEntities(LivingEntity observationObject, List<EntityType> entityType, 
			int radius, double cosDetectionAngle) {
		List<LivingEntity> targets = new ArrayList<LivingEntity>();
		for (Entity entity : observationObject.getNearbyEntities(radius, radius, radius)) {
			if (!(entity instanceof LivingEntity)) {continue;}
			if (!entityType.contains(entity.getType())) {continue;}
			LivingEntity possibleTarget = (LivingEntity)entity;
			if (!isLookingAt(possibleTarget, observationObject, radius, cosDetectionAngle)) {continue;}
			targets.add(possibleTarget);
		}
		return targets;
	}
	
	public static List<LivingEntity> getLookingEntities(LivingEntity observationObject, List<EntityType> entityType, 
			int radius, double cosDetectionAngle, int maxCount) {
		List<LivingEntity> targets = new ArrayList<LivingEntity>();
		TreeMap<Double, LivingEntity> possibleTargets = new TreeMap<Double, LivingEntity>();
		for (Entity entity : observationObject.getNearbyEntities(radius, radius, radius)) {
			if (!(entity instanceof LivingEntity)) {continue;}
			if (!entityType.contains(entity.getType())) {continue;}
			LivingEntity possibleTarget = (LivingEntity)entity;
			if (!isLookingAt(possibleTarget, observationObject, radius, cosDetectionAngle)) {continue;}
			possibleTargets.put(observationObject.getLocation().distance(possibleTarget.getLocation()), possibleTarget);
		}
		for (int i=0; i<possibleTargets.size(); i++) {
			double firstKey = possibleTargets.firstKey();
			LivingEntity target = possibleTargets.get(firstKey);
			possibleTargets.remove(firstKey);
			targets.add(target);
			if (i >= maxCount) {break;}
		}
		return targets;
	}
	
	public static List<LivingEntity> getUnderAbservation(LivingEntity beholder, 
			List<EntityType> entityType, int distance, double cosDetectionAngle){
		List<LivingEntity> targets = new ArrayList<LivingEntity>();
		for (Entity entity : beholder.getNearbyEntities(distance, distance, distance)) {
			if (!(entity instanceof LivingEntity)) {continue;}
			if (!entityType.contains(entity.getType())) {continue;}
			LivingEntity possibleTarget = (LivingEntity)entity;
			if (!isLookingAt(beholder, possibleTarget, distance, cosDetectionAngle)) {continue;}
			targets.add(possibleTarget);
		}
		return targets;
	}
	
	public static List<LivingEntity> getUnderAbservation(LivingEntity beholder, 
			List<EntityType> entityType, int distance, double cosDetectionAngle, int maxCount){
		List<LivingEntity> targets = new ArrayList<LivingEntity>();
		TreeMap<Double, LivingEntity> possibleTargets = new TreeMap<Double, LivingEntity>();
		for (Entity entity : beholder.getNearbyEntities(distance, distance, distance)) {
			if (!(entity instanceof LivingEntity)) {continue;}
			if (!entityType.contains(entity.getType())) {continue;}
			LivingEntity possibleTarget = (LivingEntity)entity;
			if (!isLookingAt(beholder, possibleTarget, distance, cosDetectionAngle)) {continue;}
			possibleTargets.put(beholder.getLocation().distance(possibleTarget.getLocation()), possibleTarget);
		}
		for (int i=0; i<possibleTargets.size(); i++) {
			double firstKey = possibleTargets.firstKey();
			LivingEntity target = possibleTargets.get(firstKey);
			possibleTargets.remove(firstKey);
			targets.add(target);
			if (i >= maxCount) {break;}
		}
		return targets;
	}
	
	public static Block getFirstBlockIntersectsVector(
			World world, Vector start, Vector direction, double yOffset, int maxDistance) {
		BlockIterator blockIterator = new BlockIterator(world, start, direction, yOffset, maxDistance);
		Block block;
		while (blockIterator.hasNext()) {
			block = blockIterator.next();
			if (block.getType()!=Material.AIR) {return block;}
		}
		return null;
	}
	
	public static boolean isLookingAt(LivingEntity beholder, LivingEntity target, 
			int distance, double cosDetectionAngle){
	    Location eye = beholder.getEyeLocation();
	    Vector fromBeholderToTarget = target.getEyeLocation().toVector().subtract(eye.toVector());
	    double dot = fromBeholderToTarget.normalize().dot(eye.getDirection());
	    Block blockIntersectsVector = getFirstBlockIntersectsVector(
	    		beholder.getWorld(), eye.toVector(), fromBeholderToTarget.normalize(), 0, distance+1);
	    if (blockIntersectsVector!=null) {
		    Location blockIntersectsVectorLoc = blockIntersectsVector.getLocation();
		    double fromBeholderToTargetDist = eye.distance(target.getEyeLocation());
		    double fromBlockToTargetDist = blockIntersectsVectorLoc.distance(target.getEyeLocation());
		    return (dot>cosDetectionAngle)&&(fromBeholderToTargetDist<fromBlockToTargetDist);
	    } else {
	    	return dot>cosDetectionAngle;
	    }
	}

}
