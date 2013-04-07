package net.undead.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelCaveDweller extends ModelBase {
	// fields
	ModelRenderer Head;
	ModelRenderer Hair;
	ModelRenderer Hair2;
	ModelRenderer Body;
	ModelRenderer Leg1;
	ModelRenderer Leg2;
	ModelRenderer Arm1;
	ModelRenderer Arm2;
	ModelRenderer Hair3;
	ModelRenderer Hair4;

	public ModelCaveDweller() {
		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-4F, -8F, -4F, 8, 8, 8);
		Head.setRotationPoint(0F, 0F, 0F);
		SetRotation(Head, 0F, 0F, 0F);
		Hair = new ModelRenderer(this, 0, 8);
		Hair.addBox(-4F, -3F, -4F, 1, 16, 8);
		Hair.setRotationPoint(0F, -5F, 0F);
		SetRotation(Hair, 0F, 0F, 0F);
		Hair2 = new ModelRenderer(this, 0, 8);
		Hair2.addBox(4F, -4F, -4F, 1, 16, 8);
		Hair2.setRotationPoint(0F, -4F, 0F);
		SetRotation(Hair2, 0F, 0F, 0F);
		Body = new ModelRenderer(this, 36, 0);
		Body.addBox(-4F, 0F, -3F, 8, 10, 6);
		Body.setRotationPoint(0F, 0F, 0F);
		SetRotation(Body, 0F, 0F, 0F);
		Leg1 = new ModelRenderer(this, 26, 13);
		Leg1.addBox(0F, 0F, -2F, 2, 14, 2);
		Leg1.setRotationPoint(2F, 10F, 0F);
		SetRotation(Leg1, 0F, 0F, 0F);
		Leg2 = new ModelRenderer(this, 26, 13);
		Leg2.addBox(-2F, 0F, -2F, 2, 14, 2);
		Leg2.setRotationPoint(-2F, 10F, 0F);
		SetRotation(Leg2, 0F, 0F, 0F);
		Arm1 = new ModelRenderer(this, 52, 14);
		Arm1.addBox(-2F, 0F, -2.5F, 2, 13, 5);
		Arm1.setRotationPoint(-4F, 0F, 0F);
		SetRotation(Arm1, 0F, 0F, 0F);
		Arm2 = new ModelRenderer(this, 52, 14);
		Arm2.addBox(0F, 0F, -2.5F, 2, 13, 5);
		Arm2.setRotationPoint(4F, 0F, 0F);
		SetRotation(Arm2, 0F, 0F, 0F);
		Hair3 = new ModelRenderer(this, 0, 17);
		Hair3.addBox(-4F, -4F, 4F, 8, 16, 1);
		Hair3.setRotationPoint(0F, -4F, 0F);
		Hair4 = new ModelRenderer(this, 0, 17);
		SetRotation(Hair3, 0F, 0F, 0F);
		Hair4.addBox(-4F, -4F, -4F, 8, 16, 1);
		Hair4.setRotationPoint(0F, -4F, 0F);
		SetRotation(Hair4, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Head.render(f5);
		Hair.render(f5);
		Hair2.render(f5);
		Body.render(f5);
		Leg1.render(f5);
		Leg2.render(f5);
		Arm1.render(f5);
		Arm2.render(f5);
		Hair3.render(f5);
		Hair4.render(f5);

	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		Head.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1
				* 0.5F;
		Head.rotateAngleX = f4 / 57.29578F;
		Hair.rotateAngleY = Head.rotateAngleY;
		Hair.rotateAngleX = f4 / 57.29578F;
		Hair2.rotateAngleY = Head.rotateAngleY;
		Hair2.rotateAngleX = f4 / 57.29578F;
		Hair3.rotateAngleY = Head.rotateAngleY;
		Hair3.rotateAngleX = f4 / 57.29578F;
		Hair4.rotateAngleY = Head.rotateAngleY;
		Hair4.rotateAngleX = f4 / 57.29578F;
		Arm1.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1
				* 0.5F;
		Arm2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
		Arm1.rotateAngleZ = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.2F;
		Arm2.rotateAngleZ = MathHelper.cos(f * 0.7662F) * 2.0F * f1 * 0.2F;
		Leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		Leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
		Leg1.rotateAngleY = 0.0F;
		Leg2.rotateAngleY = 0.0F;
	}

	private void SetRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
