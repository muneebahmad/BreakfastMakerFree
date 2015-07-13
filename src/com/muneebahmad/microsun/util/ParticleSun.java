/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muneebahmad.microsun.util;

import com.algorithmi.maker.breakfast.free.main.R;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.particle.ParticleSystem;
import com.wiyun.engine.particle.QuadParticleSystem;

/**
 *
 * @author muneebahmad
 */
public class ParticleSun extends QuadParticleSystem {
    
    /**
     *
     * @return
     */
    public static ParticleSystem make() {
        return new ParticleSun();
    }
    
    /**
     *
     */
    protected ParticleSun() {
        this(350);
    }
    
    /**
     *
     * @param p
     */
    protected ParticleSun(int p) {
        super(p);
        //additive
        setBlendAdditive(true);
        //duration
        setDuration(PARTICLE_DURATION_INFINITY);
        //angle
        setDirectionAngleVariance(90, 360);
        //life of particles
        setLifeVariance(1.0f, 0.5f);
        //speed of particles
        setSpeedVariance(20, 5);
        //seize in pixles
        setStartSizeVariance(30.0f, 10.0f);
        //emits per second
        setEmissionRate(getMaxParticleCount() / getLife());
        //color of particles
        setStartColorVariance(0.76f, 0.25f, 0.12f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        setEndColorVariance(0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        //TODO support fire.pvr
        setTexture(Texture2D.make("art/fire.png"));
    }  
}//end class
