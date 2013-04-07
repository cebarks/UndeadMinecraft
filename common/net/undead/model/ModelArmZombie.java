package net.undead.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelArmZombie extends ModelBase {
	// fields
	ModelRenderer Shape1;
	ModelRenderer head;
	ModelRenderer Shape3;
	ModelRenderer body;
	ModelRenderer rightarm;
	ModelRenderer leftarm;
	ModelRenderer rightleg;
	ModelRenderer leftleg;
	ModelRenderer Shape2;

	public ModelArmZombie() {
		Shape1 = new ModelRenderer(this, 0, 11);
		Shape1.addBox(1F, 8F, -3F, 8, 6, 4);
		Shape1.setRotationPoint(-5F, -2F, 0F);
		setRotation(Shape1, 0F, 0F, 0F);
		head = new ModelRenderer(this, 36, 18);
		head.addBox(-4F, -6F, -7F, 7, 7, 7);
		head.setRotationPoint(1F, -1F, -2F);
		setRotation(head, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 4, 22);
		Shape3.addBox(0F, 0F, 0F, 4, 6, 4);
		Shape3.setRotationPoint(-4F, 18F, -3F);
		setRotation(Shape3, 0F, 0F, 0F);
		body = new ModelRenderer(this, 36, -1);
		body.addBox(-4F, 0F, -4F, 8, 7, 6);
		body.setRotationPoint(0F, 0F, -2F);
		setRotation(body, 0F, 0F, 0F);
		rightarm = new ModelRenderer(this, 24, 6);
		rightarm.addBox(-3F, -2F, -2F, 3, 11, 3);
		rightarm.setRotationPoint(7F, 12F, -1F);
		setRotation(rightarm, -2F, 0F, 0F);
		leftarm = new ModelRenderer(this, 24, 6);
		leftarm.addBox(-1F, -2F, -2F, 3, 12, 3);
		leftarm.setRotationPoint(5F, 2F, 0F);
		setRotation(leftarm, -1F, 0F, 0F);
		rightleg = new ModelRenderer(this, 20, 21);
		rightleg.addBox(-2F, 0F, -2F, 4, 7, 4);
		rightleg.setRotationPoint(-2F, 12F, 0F);
		setRotation(rightleg, 0F, 0F, 0F);
		leftleg = new ModelRenderer(this, 20, 21);
		leftleg.addBox(-2F, 0F, -2F, 4, 7, 4);
		leftleg.setRotationPoint(2F, 12F, 0F);
		setRotation(leftleg, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 4, 22);
		Shape2.addBox(0F, 0F, -1F, 4, 6, 4);
		Shape2.setRotationPoint(0F, 18F, -1F);
		setRotation(Shape2, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape1.render(f5);
		head.render(f5);
		Shape3.render(f5);
		body.render(f5);
		rightarm.render(f5);
		leftarm.render(f5);
		rightleg.render(f5);
		leftleg.render(f5);
		Shape2.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F
				* f1;
		rightleg.rotateAngleY = 0.0F;
		rightleg.rotateAngleY = 0.0F;
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
