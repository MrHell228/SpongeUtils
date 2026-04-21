package net.hellheim.spongetools.math.set;

import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;

import net.hellheim.spongetools.math.mutable.vector.MutableVector2i;
import net.hellheim.spongetools.object.Streamable;

public class Manhattan2 implements CountableVectorSet.Vec2i {
	
	private final int xDepth;
	private final int yDepth;
	private final int totalDepth;
	
	protected Manhattan2(final int xDepth, final int yDepth) {
		Preconditions.checkArgument(xDepth >= 0, "xDepth must not be negative");
		Preconditions.checkArgument(yDepth >= 0, "yDepth must not be negative");
		this.xDepth = xDepth;
		this.yDepth = yDepth;
		this.totalDepth = xDepth + yDepth;
	}
	
	public int xDepth() {
		return this.xDepth;
	}
	
	public int yDepth() {
		return this.yDepth;
	}
	
	@Override
	public Streamable<MutableVector2i> all() {
		return Streamable.of(() -> new AbstractIterator<MutableVector2i>() {
			private final MutableVector2i cursor = MutableVector2i.zero();
			private int currentDepth;
			private int xMax;
			private int x;
			private boolean yMirror;
			
			@Override
			protected MutableVector2i computeNext() {
				if (this.yMirror) {
					this.yMirror = false;
					return this.cursor.set(this.x - 1, -this.cursor.y());
				}
				
				MutableVector2i vec;
				for (vec = null; vec == null; this.x++) {
					if (this.x > this.xMax) {
						this.currentDepth++;
						if (this.currentDepth > Manhattan2.this.totalDepth) {
							return this.endOfData();
						}
						
						this.xMax = Math.min(Manhattan2.this.xDepth, this.currentDepth);
						this.x = -this.xMax;
					}
					
					final int y = this.currentDepth - Math.abs(this.x);
					if (y <= Manhattan2.this.yDepth) {
						this.yMirror = y != 0;
						vec = this.cursor.set(this.x, y);
					}
				}
				
				return vec;
			};
		});
	}
	
	@Override
	public Streamable<MutableVector2i> random(final RandomGenerator random) {
		return Streamable.of(() -> {
			final MutableVector2i cursor = MutableVector2i.zero();
			return Stream.generate(() -> {
				int x = random.nextInt(this.xDepth + 1);
				int y = random.nextInt(this.yDepth + 1);
				
				if ((x + y) > this.totalDepth) {
					x = this.xDepth - x;
					y = this.yDepth - y;
				}
				
				if (random.nextBoolean()) {
					x = -x;
				}
				if (random.nextBoolean()) {
					y = -y;
				}
				
				return cursor.set(x, y);
			});
		});
	}
}
