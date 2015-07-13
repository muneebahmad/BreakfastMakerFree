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
public class ParticleSmoke extends QuadParticleSystem {
    
    /**
     *
     * @return
     */
    public static ParticleSystem make() {
        return new ParticleSmoke();
    }
    
    /**
     *
     */
    protected ParticleSmoke() {
        this(200);
    }
    
    /**
     *
     * @param p
     */
    protected ParticleSmoke(int p) {
        super(p);
        //duration
        setDuration(PARTICLE_DURATION_INFINITY);
        //angle
        setDirectionAngleVariance(120, 5);
        //position var
        setParticlePositionVariance(0, 0, 20, 0);
        //life of particles
        setLifeVariance(4, 1);
        //speed of particles
        setSpeedVariance(25, 10);
        //size in px
        setStartSizeVariance(60, 10);
        //emits per frame
        setEmissionRate(getMaxParticleCount() / getLife());
        //color of particles
        setStartColorVariance(0.8f, 0.8f, 0.8f, 1.0f, 0.02f, 0.02f, 0.02f, 0.0f);
        setEndColorVariance(0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        //setTexture
        setTexture(Texture2D.make("art/fire.png"));
        //additive
        setBlendAdditive(false);
    }
}//end class
