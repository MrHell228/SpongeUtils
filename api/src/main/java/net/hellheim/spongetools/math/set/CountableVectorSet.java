package net.hellheim.spongetools.math.set;

import java.util.random.RandomGenerator;

import org.spongepowered.math.vector.Vector2i;
import org.spongepowered.math.vector.Vector3i;
import org.spongepowered.math.vector.Vector4i;

import net.hellheim.spongetools.math.mutable.vector.MutableVector2i;
import net.hellheim.spongetools.math.mutable.vector.MutableVector3i;
import net.hellheim.spongetools.math.mutable.vector.MutableVector4i;
import net.hellheim.spongetools.object.Streamable;

/**
 * @param <V> The vector type
 */
public interface CountableVectorSet<V> {
	
	Streamable<V> all();
	
	/**
	 * @return The infinite Streamable
	 */
	Streamable<V> random(RandomGenerator random);
	
	interface Vec2i extends CountableVectorSet<MutableVector2i> {
		
		default Streamable<MutableVector2i> all(final Vector2i translation) {
			return this.all(translation.x(), translation.y());
		}
		
		default Streamable<MutableVector2i> all(final int xTranslation, final int yTranslation) {
			return this.all().map(v -> v.add(xTranslation, yTranslation));
		}
		
		default Streamable<MutableVector2i> random(final RandomGenerator random, final Vector2i translation) {
			return this.random(random, translation.x(), translation.y());
		}
		
		default Streamable<MutableVector2i> random(final RandomGenerator random, final int xTranslation, final int yTranslation) {
			return this.random(random).map(v -> v.add(xTranslation, yTranslation));
		}
	}
	
	interface Vec3i extends CountableVectorSet<MutableVector3i> {
		
		default Streamable<MutableVector3i> all(final Vector3i translation) {
			return this.all(translation.x(), translation.y(), translation.z());
		}
		
		default Streamable<MutableVector3i> all(final int xTranslation, final int yTranslation, final int zTranslation) {
			return this.all().map(v -> v.add(xTranslation, yTranslation, zTranslation));
		}
		
		default Streamable<MutableVector3i> random(final RandomGenerator random, final Vector3i translation) {
			return this.random(random, translation.x(), translation.y(), translation.z());
		}
		
		default Streamable<MutableVector3i> random(final RandomGenerator random, final int xTranslation, final int yTranslation, final int zTranslation) {
			return this.random(random).map(v -> v.add(xTranslation, yTranslation, zTranslation));
		}
	}
	
	interface Vec4i extends CountableVectorSet<MutableVector4i> {
		
		default Streamable<MutableVector4i> all(final Vector4i translation) {
			return this.all(translation.x(), translation.y(), translation.z(), translation.w());
		}
		
		default Streamable<MutableVector4i> all(final int xTranslation, final int yTranslation, final int zTranslation, final int wTranslation) {
			return this.all().map(v -> v.add(xTranslation, yTranslation, zTranslation, wTranslation));
		}
		
		default Streamable<MutableVector4i> random(final RandomGenerator random, final Vector4i translation) {
			return this.random(random, translation.x(), translation.y(), translation.z(), translation.w());
		}
		
		default Streamable<MutableVector4i> random(final RandomGenerator random, final int xTranslation, final int yTranslation, final int zTranslation, final int wTranslation) {
			return this.random(random).map(v -> v.add(xTranslation, yTranslation, zTranslation, wTranslation));
		}
	}
}
