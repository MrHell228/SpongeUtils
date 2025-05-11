package net.hellheim.spongetools.resourcepack;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(ModelTemplates.class)
public interface ModelTemplateProvider extends Supplier<ModelTemplate> {
	
	private static ModelTemplate validate(final ModelTemplate template, final int size) {
		final int slots = Objects.requireNonNull(template, "template").slots().size();
		if (slots != size) {
			throw new IllegalArgumentException("Template must have " + size + " slots, " + slots + " provided.");
		}
		
		return template;
	}
	
	static ModelTemplateProvider.T1 t1(final ModelTemplate template) {
		ModelTemplateProvider.validate(template, 1);
		return () -> template;
	}
	
	static ModelTemplateProvider.T2 t2(final ModelTemplate template) {
		ModelTemplateProvider.validate(template, 2);
		return () -> template;
	}
	
	static ModelTemplateProvider.T3 t3(final ModelTemplate template) {
		ModelTemplateProvider.validate(template, 3);
		return () -> template;
	}
	
	static ModelTemplateProvider.T4 t4(final ModelTemplate template) {
		ModelTemplateProvider.validate(template, 4);
		return () -> template;
	}
	
	static ModelTemplateProvider.T5 t5(final ModelTemplate template) {
		ModelTemplateProvider.validate(template, 5);
		return () -> template;
	}
	
	static ModelTemplateProvider.T6 t6(final ModelTemplate template) {
		ModelTemplateProvider.validate(template, 6);
		return () -> template;
	}
	
	static ModelTemplateProvider.T7 t7(final ModelTemplate template) {
		ModelTemplateProvider.validate(template, 7);
		return () -> template;
	}
	
	
	
	default Optional<ResourceKey> key() {
		return this.get().key();
	}
	
	default Optional<String> suffix() {
		return this.get().suffix();
	}
	
	default List<TextureSlot> slots() {
		return this.get().slots();
	}
	
	public interface T1 extends ModelTemplateProvider {
		
		default TextureSlot slot() {
			return this.slots().get(0);
		}
		
		default TexturedModel textured(
			final ResourceKey texture
		) {
			return TexturedModel.of(this, Textures.of(
					this.slot(), texture
					));
		}
		
		default TexturedModel textured(
			final ResourceKey texture,
			final String suffix
		) {
			return TexturedModel.of(this, Textures.of(
					texture,
					this.slot(), suffix
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture
		) {
			return TexturedModel.of(this, Textures.block(
					this.slot(), texture
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture,
			final String suffix
		) {
			return TexturedModel.of(this, Textures.block(
					texture,
					this.slot(), suffix
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture
		) {
			return TexturedModel.of(this, Textures.item(
					this.slot(), texture
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture,
			final String suffix
		) {
			return TexturedModel.of(this, Textures.item(
					texture,
					this.slot(), suffix
					));
		}
	}
	
	public interface T2 extends ModelTemplateProvider {
		
		default TextureSlot slot1() {
			return this.slots().get(0);
		}
		
		default TextureSlot slot2() {
			return this.slots().get(1);
		}
		
		default TexturedModel textured(
			final ResourceKey texture1,
			final ResourceKey texture2
		) {
			return TexturedModel.of(this, Textures.of(
					this.slot1(), texture1,
					this.slot2(), texture2
					));
		}
		
		default TexturedModel textured(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2
		) {
			return TexturedModel.of(this, Textures.of(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture1,
			final ResourceKey texture2
		) {
			return TexturedModel.of(this, Textures.block(
					this.slot1(), texture1,
					this.slot2(), texture2
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2
		) {
			return TexturedModel.of(this, Textures.block(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture1,
			final ResourceKey texture2
		) {
			return TexturedModel.of(this, Textures.item(
					this.slot1(), texture1,
					this.slot2(), texture2
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2
		) {
			return TexturedModel.of(this, Textures.item(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2
					));
		}
	}
	
	public interface T3 extends ModelTemplateProvider {
		
		default TextureSlot slot1() {
			return this.slots().get(0);
		}
		
		default TextureSlot slot2() {
			return this.slots().get(1);
		}
		
		default TextureSlot slot3() {
			return this.slots().get(2);
		}
		
		default TexturedModel textured(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3
		) {
			return TexturedModel.of(this, Textures.of(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3
					));
		}
		
		default TexturedModel textured(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3
		) {
			return TexturedModel.of(this, Textures.of(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3
		) {
			return TexturedModel.of(this, Textures.block(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3
		) {
			return TexturedModel.of(this, Textures.block(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3
		) {
			return TexturedModel.of(this, Textures.item(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3
		) {
			return TexturedModel.of(this, Textures.item(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3
					));
		}
	}
	
	public interface T4 extends ModelTemplateProvider {
		
		default TextureSlot slot1() {
			return this.slots().get(0);
		}
		
		default TextureSlot slot2() {
			return this.slots().get(1);
		}
		
		default TextureSlot slot3() {
			return this.slots().get(2);
		}
		
		default TextureSlot slot4() {
			return this.slots().get(3);
		}
		
		default TexturedModel textured(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3,
			final ResourceKey texture4
		) {
			return TexturedModel.of(this, Textures.of(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3,
					this.slot4(), texture4
					));
		}
		
		default TexturedModel textured(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3,
			final String suffix4
		) {
			return TexturedModel.of(this, Textures.of(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3,
					this.slot4(), suffix4
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3,
			final ResourceKey texture4
		) {
			return TexturedModel.of(this, Textures.block(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3,
					this.slot4(), texture4
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3,
			final String suffix4
		) {
			return TexturedModel.of(this, Textures.block(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3,
					this.slot4(), suffix4
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3,
			final ResourceKey texture4
		) {
			return TexturedModel.of(this, Textures.item(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3,
					this.slot4(), texture4
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3,
			final String suffix4
		) {
			return TexturedModel.of(this, Textures.item(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3,
					this.slot4(), suffix4
					));
		}
	}
	
	public interface T5 extends ModelTemplateProvider {
		
		default TextureSlot slot1() {
			return this.slots().get(0);
		}
		
		default TextureSlot slot2() {
			return this.slots().get(1);
		}
		
		default TextureSlot slot3() {
			return this.slots().get(2);
		}
		
		default TextureSlot slot4() {
			return this.slots().get(3);
		}
		
		default TextureSlot slot5() {
			return this.slots().get(4);
		}
		
		default TexturedModel textured(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3,
			final ResourceKey texture4,
			final ResourceKey texture5
		) {
			return TexturedModel.of(this, Textures.of(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3,
					this.slot4(), texture4,
					this.slot5(), texture5
					));
		}
		
		default TexturedModel textured(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3,
			final String suffix4,
			final String suffix5
		) {
			return TexturedModel.of(this, Textures.of(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3,
					this.slot4(), suffix4,
					this.slot5(), suffix5
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3,
			final ResourceKey texture4,
			final ResourceKey texture5
		) {
			return TexturedModel.of(this, Textures.block(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3,
					this.slot4(), texture4,
					this.slot5(), texture5
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3,
			final String suffix4,
			final String suffix5
		) {
			return TexturedModel.of(this, Textures.block(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3,
					this.slot4(), suffix4,
					this.slot5(), suffix5
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3,
			final ResourceKey texture4,
			final ResourceKey texture5
		) {
			return TexturedModel.of(this, Textures.item(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3,
					this.slot4(), texture4,
					this.slot5(), texture5
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3,
			final String suffix4,
			final String suffix5
		) {
			return TexturedModel.of(this, Textures.item(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3,
					this.slot4(), suffix4,
					this.slot5(), suffix5
					));
		}
	}
	
	public interface T6 extends ModelTemplateProvider {
		
		default TextureSlot slot1() {
			return this.slots().get(0);
		}
		
		default TextureSlot slot2() {
			return this.slots().get(1);
		}
		
		default TextureSlot slot3() {
			return this.slots().get(2);
		}
		
		default TextureSlot slot4() {
			return this.slots().get(3);
		}
		
		default TextureSlot slot5() {
			return this.slots().get(4);
		}
		
		default TextureSlot slot6() {
			return this.slots().get(5);
		}
		
		default TexturedModel textured(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3,
			final ResourceKey texture4,
			final ResourceKey texture5,
			final ResourceKey texture6
		) {
			return TexturedModel.of(this, Textures.of(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3,
					this.slot4(), texture4,
					this.slot5(), texture5,
					this.slot6(), texture6
					));
		}
		
		default TexturedModel textured(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3,
			final String suffix4,
			final String suffix5,
			final String suffix6
		) {
			return TexturedModel.of(this, Textures.of(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3,
					this.slot4(), suffix4,
					this.slot5(), suffix5,
					this.slot6(), suffix6
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3,
			final ResourceKey texture4,
			final ResourceKey texture5,
			final ResourceKey texture6
		) {
			return TexturedModel.of(this, Textures.block(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3,
					this.slot4(), texture4,
					this.slot5(), texture5,
					this.slot6(), texture6
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3,
			final String suffix4,
			final String suffix5,
			final String suffix6
		) {
			return TexturedModel.of(this, Textures.block(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3,
					this.slot4(), suffix4,
					this.slot5(), suffix5,
					this.slot6(), suffix6
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3,
			final ResourceKey texture4,
			final ResourceKey texture5,
			final ResourceKey texture6
		) {
			return TexturedModel.of(this, Textures.item(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3,
					this.slot4(), texture4,
					this.slot5(), texture5,
					this.slot6(), texture6
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3,
			final String suffix4,
			final String suffix5,
			final String suffix6
		) {
			return TexturedModel.of(this, Textures.item(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3,
					this.slot4(), suffix4,
					this.slot5(), suffix5,
					this.slot6(), suffix6
					));
		}
	}
	
	public interface T7 extends ModelTemplateProvider {
		
		default TextureSlot slot1() {
			return this.slots().get(0);
		}
		
		default TextureSlot slot2() {
			return this.slots().get(1);
		}
		
		default TextureSlot slot3() {
			return this.slots().get(2);
		}
		
		default TextureSlot slot4() {
			return this.slots().get(3);
		}
		
		default TextureSlot slot5() {
			return this.slots().get(4);
		}
		
		default TextureSlot slot6() {
			return this.slots().get(5);
		}
		
		default TextureSlot slot7() {
			return this.slots().get(6);
		}
		
		default TexturedModel textured(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3,
			final ResourceKey texture4,
			final ResourceKey texture5,
			final ResourceKey texture6,
			final ResourceKey texture7
		) {
			return TexturedModel.of(this, Textures.of(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3,
					this.slot4(), texture4,
					this.slot5(), texture5,
					this.slot6(), texture6,
					this.slot7(), texture7
					));
		}
		
		default TexturedModel textured(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3,
			final String suffix4,
			final String suffix5,
			final String suffix6,
			final String suffix7
		) {
			return TexturedModel.of(this, Textures.of(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3,
					this.slot4(), suffix4,
					this.slot5(), suffix5,
					this.slot6(), suffix6,
					this.slot7(), suffix7
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3,
			final ResourceKey texture4,
			final ResourceKey texture5,
			final ResourceKey texture6,
			final ResourceKey texture7
		) {
			return TexturedModel.of(this, Textures.block(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3,
					this.slot4(), texture4,
					this.slot5(), texture5,
					this.slot6(), texture6,
					this.slot7(), texture7
					));
		}
		
		default TexturedModel texturedBlock(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3,
			final String suffix4,
			final String suffix5,
			final String suffix6,
			final String suffix7
		) {
			return TexturedModel.of(this, Textures.block(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3,
					this.slot4(), suffix4,
					this.slot5(), suffix5,
					this.slot6(), suffix6,
					this.slot7(), suffix7
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture1,
			final ResourceKey texture2,
			final ResourceKey texture3,
			final ResourceKey texture4,
			final ResourceKey texture5,
			final ResourceKey texture6,
			final ResourceKey texture7
		) {
			return TexturedModel.of(this, Textures.item(
					this.slot1(), texture1,
					this.slot2(), texture2,
					this.slot3(), texture3,
					this.slot4(), texture4,
					this.slot5(), texture5,
					this.slot6(), texture6,
					this.slot7(), texture7
					));
		}
		
		default TexturedModel texturedItem(
			final ResourceKey texture,
			final String suffix1,
			final String suffix2,
			final String suffix3,
			final String suffix4,
			final String suffix5,
			final String suffix6,
			final String suffix7
		) {
			return TexturedModel.of(this, Textures.item(
					texture,
					this.slot1(), suffix1,
					this.slot2(), suffix2,
					this.slot3(), suffix3,
					this.slot4(), suffix4,
					this.slot5(), suffix5,
					this.slot6(), suffix6,
					this.slot7(), suffix7
					));
		}
	}
}
