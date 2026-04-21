package net.hellheim.spongetools.math.set;

import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;

import net.hellheim.spongetools.math.mutable.vector.MutableVector2i;
import net.hellheim.spongetools.object.Streamable;

public class Ellipse implements CountableVectorSet.Vec2i {
	
	private final int xRad;
	private final int yRad;
	
	private final int xRad2;
	private final int yRad2;
	private final double xMul;
	
	protected Ellipse(final int xRadius, final int yRadius) {
		Preconditions.checkArgument(xRadius > 0, "xRadius must be positive");
		Preconditions.checkArgument(yRadius > 0, "yRadius must be positive");
		this.xRad = xRadius;
		this.yRad = yRadius;
		
		this.xRad2 = xRadius * xRadius;
		this.yRad2 = yRadius * yRadius;
		this.xMul = ((double) this.yRad2) / this.xRad2;
	}
	
	public int xRadius() {
		return this.xRad;
	}
	
	public int yRadius() {
		return this.yRad;
	}
	
	public boolean isInside(final int x, final int y) {
		return this.yRad2 * x * x + this.xRad2 * y * y <= this.xRad2 * this.yRad2;
	}
	
	@Override
	public Streamable<MutableVector2i> all() {
		return Streamable.of(() -> new AbstractIterator<MutableVector2i>() {
			private final MutableVector2i cursor = MutableVector2i.zero();
			private boolean yMirror = false;
			private int x = - Ellipse.this.xRad - 1;
			private int y;
			private int y2Max;
			
			@Override
			protected MutableVector2i computeNext() {
				if (this.yMirror) {
					this.yMirror = false;
					return this.cursor.set(this.x, this.y);
				}
				
				++this.y;
				if (this.y * this.y <= this.y2Max) {
					this.yMirror = true;
					return this.cursor.set(this.x, this.y);
				}
				
				++this.x;
				if (this.x > Ellipse.this.xRad) {
					return this.endOfData();
				}
				
				this.y = 0;
				this.y2Max = (int) (Ellipse.this.yRad2 - Ellipse.this.xMul * this.x);
				return this.cursor.set(this.x, 0);
			}
		});
	}
	
	@Override
	public Streamable<MutableVector2i> random(final RandomGenerator random) {
		return Streamable.of(() -> {
			final MutableVector2i cursor = MutableVector2i.zero();
			return Stream.generate(() -> {
				final int x = random.nextInt(-this.xRad, this.xRad + 1);
				final int y = random.nextInt(-this.yRad, this.yRad + 1);
				return cursor.set(x, y);
			}).filter(v -> this.isInside(v.x(), v.y()));
		});
	}
}
