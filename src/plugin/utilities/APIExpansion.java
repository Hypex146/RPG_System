package plugin.utilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class APIExpansion {
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
			int Distance, double cosDetectionAngle){
	    Location eye = beholder.getEyeLocation();
	    Vector fromBeholderToTarget = target.getEyeLocation().toVector().subtract(eye.toVector());
	    double dot = fromBeholderToTarget.normalize().dot(eye.getDirection());
	    Block blockIntersectsVector = getFirstBlockIntersectsVector(
	    		beholder.getWorld(), eye.toVector(), fromBeholderToTarget.normalize(), 0, Distance+1);
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
