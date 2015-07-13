package com.muneebahmad.microsun.util;

/*
 * @author muneebahmad
 */

import com.algorithmi.maker.breakfast.free.main.R;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.particle.ParticleSystem;
import com.wiyun.engine.particle.QuadParticleSystem;

/**
 *
 * @author muneebahmad
 */
public class ParticlePouringLiquid extends QuadParticleSystem {

	/**
     *
     */
    protected ParticlePouringLiquid()
	  {
	    this(1000);
	  }

	  /**
     *
     * @param paramInt
     */
    protected ParticlePouringLiquid(int paramInt)
	  {
	    super(paramInt);
	    setDuration(-1.0F);
	    setParticleGravity(0F, 70.0F);
	    setDirectionAngleVariance(-90.0F, 7.0F);
	    setSpeedVariance(130.0F, 30.0F);
	    setRadialAccelerationVariance(0F, 1F);
	    setTangentialAccelerationVariance(0F, 1F);
	    setLifeVariance(1.5F, 0F);
	    setStartSizeVariance(5.0F, 10.0F);
	    setEndSizeVariance(5.0F, 6.0F);
	    setEmissionRate(getMaxParticleCount() / getLife());
	    setStartColorVariance(0.89999997615814208984F, 0.89999997615814208984F, 0.89999997615814208984F, 1F, 0F, 0F, 0.10000000149011611938F, 0F);
	    setEndColorVariance(1F, 1F, 1F, 0F, 0F, 0F, 0F, 0F);
	    setTexture(Texture2D.make("art/snow.png"));
	    setBlendAdditive(false);
	    setAutoRemoveOnFinish(true);
	  }

	  /**
     *
     * @return
     */
    public static ParticleSystem make()
	  {
	    return new ParticlePouringLiquid();
	  }
	
}//end class
