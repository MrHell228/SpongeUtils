package net.hellheim.spongetools.math.set;

import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;

import net.hellheim.spongetools.math.mutable.vector.MutableVector3i;
import net.hellheim.spongetools.object.Streamable;

public class Manhattan3 implements CountableVectorSet.Vec3i {
	
	private final int xDepth;
	private final int yDepth;
	private final int zDepth;
	private final long totalDepth;
	
	protected Manhattan3(final int xDepth, final int yDepth, final int zDepth) {
		Preconditions.checkArgument(xDepth >= 0, "xDepth must not be negative");
		Preconditions.checkArgument(yDepth >= 0, "yDepth must not be negative");
		Preconditions.checkArgument(zDepth >= 0, "zDepth must not be negative");
		this.xDepth = xDepth;
		this.yDepth = yDepth;
		this.zDepth = zDepth;
		this.totalDepth = xDepth + yDepth + zDepth;
	}
	
	public int xDepth() {
		return this.xDepth;
	}
	
	public int yDepth() {
		return this.yDepth;
	}
	
	public int zDepth() {
		return this.zDepth;
	}
	
	@Override
	public Streamable<MutableVector3i> all() {
		// Copied from Minecraft's BlockPos#withinManhattan
		return Streamable.of(() -> new AbstractIterator<MutableVector3i>() {
			private final MutableVector3i cursor = MutableVector3i.zero();
			private int currentDepth;
			private int xMax;
			private int yMax;
			private int x;
			private int y;
			private boolean zMirror;
			
			@Override
			protected MutableVector3i computeNext() {
				if (this.zMirror) {
					this.zMirror = false;
					return this.cursor.set(this.x, this.y - 1, -this.cursor.z());
				}
				
				MutableVector3i vec;
				for (vec = null; vec == null; this.y++) {
					if (this.y > this.yMax) {
						this.x++;
						if (this.x > this.xMax) {
							this.currentDepth++;
							if (this.currentDepth > Manhattan3.this.totalDepth) {
								return this.endOfData();
							}
							
							this.xMax = Math.min(Manhattan3.this.xDepth, this.currentDepth);
							this.x = -this.xMax;
						}
						
						this.yMax = Math.min(Manhattan3.this.yDepth, this.currentDepth - Math.abs(this.x));
						this.y = -this.yMax;
					}
					
					final int z = this.currentDepth - Math.abs(this.x) - Math.abs(this.y);
					if (z <= Manhattan3.this.zDepth) {
						this.zMirror = z != 0;
						vec = this.cursor.set(this.x, this.y, z);
					}
				}
				
				return vec;
			}
		});
	}
	
	@Override
	public Streamable<MutableVector3i> random(final RandomGenerator random) {
		return Streamable.of(() -> {
			final MutableVector3i cursor = MutableVector3i.zero();
			return Stream.generate(() -> {
				int x = random.nextInt(this.xDepth + 1);
				int y = random.nextInt(this.yDepth + 1);
				int z = random.nextInt(this.zDepth + 1);
				
				if ((x + y + z) > this.totalDepth) {
					x = this.xDepth - x;
					y = this.yDepth - y;
					z = this.zDepth - z;
				}
				
				if (random.nextBoolean()) {
					x = -x;
				}
				if (random.nextBoolean()) {
					y = -y;
				}
				if (random.nextBoolean()) {
					z = -z;
				}
				
				return cursor.set(x, y, z);
			});
		});
	}
}
