package net.undead.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBike extends ModelBase {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8;
	ModelRenderer Shape9;
	ModelRenderer Shape10;
	ModelRenderer Shape11;
	ModelRenderer Shape12;
	ModelRenderer Shape13;
	ModelRenderer Shape14;
	ModelRenderer Shape15;
	ModelRenderer Shape16;
	ModelRenderer Shape17;
	ModelRenderer Shape18;
	ModelRenderer Shape19;
	ModelRenderer Shape20;
	ModelRenderer Shape21;
	ModelRenderer Shape22;

	public ModelBike() {
		textureWidth = 80;
		textureHeight = 80;

		Shape1 = new ModelRenderer(this, 0, 62);
		Shape1.addBox(0F, 0F, 0F, 3, 9, 9);
		Shape1.setRotationPoint(-1F, 15F, 20F);
		Shape1.setTextureSize(80, 80);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 62);
		Shape2.addBox(0F, 0F, 0F, 3, 9, 9);
		Shape2.setRotationPoint(-1F, 15F, -20F);
		Shape2.setTextureSize(80, 80);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 48, 56);
		Shape3.addBox(0F, 0F, 0F, 1, 23, 1);
		Shape3.setRotationPoint(-2F, 2F, 10F);
		Shape3.setTextureSize(80, 80);
		Shape3.mirror = true;
		setRotation(Shape3, 0.7063936F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 48, 56);
		Shape4.addBox(0F, 0F, 0F, 1, 23, 1);
		Shape4.setRotationPoint(2F, 2F, 10F);
		Shape4.setTextureSize(80, 80);
		Shape4.mirror = true;
		setRotation(Shape4, 0.7063936F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 0, 0);
		Shape5.addBox(0F, 0F, 0F, 3, 3, 19);
		Shape5.setRotationPoint(-1F, 14F, 1F);
		Shape5.setTextureSize(80, 80);
		Shape5.mirror = true;
		setRotation(Shape5, 0.5948578F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 43, 37);
		Shape6.addBox(0F, 0F, 0F, 3, 3, 16);
		Shape6.setRotationPoint(-1F, 13F, -13F);
		Shape6.setTextureSize(80, 80);
		Shape6.mirror = true;
		setRotation(Shape6, -0.0371786F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 50, 22);
		Shape7.addBox(0F, 0F, 0F, 1, 1, 14);
		Shape7.setRotationPoint(0F, 19F, 9F);
		Shape7.setTextureSize(80, 80);
		Shape7.mirror = true;
		setRotation(Shape7, 1.115358F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 24, 71);
		Shape8.addBox(0F, 0F, 0F, 5, 2, 7);
		Shape8.setRotationPoint(-2F, 14F, -20F);
		Shape8.setTextureSize(80, 80);
		Shape8.mirror = true;
		setRotation(Shape8, 0.1115358F, 0F, 0F);
		Shape9 = new ModelRenderer(this, 8, 48);
		Shape9.addBox(0F, 0F, 0F, 1, 1, 13);
		Shape9.setRotationPoint(0F, 19F, -4F);
		Shape9.setTextureSize(80, 80);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);
		Shape10 = new ModelRenderer(this, 24, 69);
		Shape10.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape10.setRotationPoint(0F, 19.1F, 8.5F);
		Shape10.setTextureSize(80, 80);
		Shape10.mirror = true;
		setRotation(Shape10, 0.6320364F, 0F, 0F);
		Shape11 = new ModelRenderer(this, 52, 69);
		Shape11.addBox(0F, 0F, 0F, 1, 1, 10);
		Shape11.setRotationPoint(2F, 3F, 1F);
		Shape11.setTextureSize(80, 80);
		Shape11.mirror = true;
		setRotation(Shape11, 0.1487144F, 0F, 0F);
		Shape12 = new ModelRenderer(this, 52, 69);
		Shape12.addBox(0F, 0F, 0F, 1, 1, 10);
		Shape12.setRotationPoint(-2F, 3F, 1F);
		Shape12.setTextureSize(80, 80);
		Shape12.mirror = true;
		setRotation(Shape12, 0.1487195F, 0F, 0F);
		Shape13 = new ModelRenderer(this, 8, 48);
		Shape13.addBox(0F, 0F, 0F, 1, 1, 13);
		Shape13.setRotationPoint(-3F, 19F, -15F);
		Shape13.setTextureSize(80, 80);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0.2443461F, 0F);
		Shape14 = new ModelRenderer(this, 8, 48);
		Shape14.addBox(0F, 0F, 0F, 1, 1, 13);
		Shape14.setRotationPoint(3F, 19F, -15F);
		Shape14.setTextureSize(80, 80);
		Shape14.mirror = true;
		setRotation(Shape14, 0F, -0.2443461F, 0F);
		Shape15 = new ModelRenderer(this, 8, 34);
		Shape15.addBox(0F, 0F, 0F, 3, 5, 9);
		Shape15.setRotationPoint(-1F, 14F, 1F);
		Shape15.setTextureSize(80, 80);
		Shape15.mirror = true;
		setRotation(Shape15, 0F, 0F, 0F);
		Shape16 = new ModelRenderer(this, 32, 63);
		Shape16.addBox(0F, 0F, 0F, 3, 3, 5);
		Shape16.setRotationPoint(-1F, 13F, 7F);
		Shape16.setTextureSize(80, 80);
		Shape16.mirror = true;
		setRotation(Shape16, 0F, 0F, 0F);
		Shape17 = new ModelRenderer(this, 12, 26);
		Shape17.addBox(0F, 0.6F, 0F, 3, 3, 5);
		Shape17.setRotationPoint(-1F, 17F, 6F);
		Shape17.setTextureSize(80, 80);
		Shape17.mirror = true;
		setRotation(Shape17, 0.8327113F, 0F, 0F);
		Shape18 = new ModelRenderer(this, 0, 31);
		Shape18.addBox(0F, 0F, 0F, 1, 11, 1);
		Shape18.setRotationPoint(-2F, 13F, 11F);
		Shape18.setTextureSize(80, 80);
		Shape18.mirror = true;
		setRotation(Shape18, -0.8705618F, 0.1135515F, 0F);
		Shape19 = new ModelRenderer(this, 0, 43);
		Shape19.addBox(0F, 0F, 0F, 2, 17, 2);
		Shape19.setRotationPoint(-3F, 19F, 4F);
		Shape19.setTextureSize(80, 80);
		Shape19.mirror = true;
		setRotation(Shape19, -1.665423F, 0.2649536F, 0F);
		Shape20 = new ModelRenderer(this, 49, 1);
		Shape20.addBox(0F, 0F, 0F, 5, 1, 11);
		Shape20.setRotationPoint(-2F, 13F, -11F);
		Shape20.setTextureSize(80, 80);
		Shape20.mirror = true;
		setRotation(Shape20, 0F, 0F, 0F);
		Shape21 = new ModelRenderer(this, 43, 33);
		Shape21.addBox(0F, 0F, 0F, 1, 2, 2);
		Shape21.setRotationPoint(2F, 15F, 7F);
		Shape21.setTextureSize(80, 80);
		Shape21.mirror = true;
		setRotation(Shape21, 0F, 0F, 0F);
		Shape22 = new ModelRenderer(this, 2, 0);
		Shape22.addBox(0F, 0F, 0F, 5, 4, 16);
		Shape22.setRotationPoint(-2F, 14F, 0F);
		Shape22.setTextureSize(80, 80);
		Shape22.mirror = true;
		setRotation(Shape22, 0.5677577F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
		Shape8.render(f5);
		Shape9.render(f5);
		Shape10.render(f5);
		Shape11.render(f5);
		Shape12.render(f5);
		Shape13.render(f5);
		Shape14.render(f5);
		Shape15.render(f5);
		Shape16.render(f5);
		Shape17.render(f5);
		Shape18.render(f5);
		Shape19.render(f5);
		Shape20.render(f5);
		Shape21.render(f5);
		Shape22.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
