/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
package org.lwjgl.opengl;

import org.lwjgl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.system.Checks.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.APIUtil.*;

/**
 * Native bindings to the <a href="http://www.opengl.org/registry/specs/ARB/sampler_objects.txt">ARB_sampler_objects</a> extension.
 * 
 * <p>In unextended OpenGL textures are considered to be sets of image data (mip-chains, arrays, cube-map face sets, etc.) and sampling state (sampling mode,
 * mip-mapping state, coordinate wrapping and clamping rules, etc.) combined into a single object. It is typical for an application to use many textures
 * with a limited set of sampling states that are the same between them. In order to use textures in this way, an application must generate and configure
 * many texture names, adding overhead both to applications and to implementations. Furthermore, should an application wish to sample from a texture in
 * more than one way (with and without mip-mapping, for example) it must either modify the state of the texture or create two textures, each with a copy of
 * the same image data. This can introduce runtime and memory costs to the application.</p>
 * 
 * <p>This extension separates sampler state from texture image data. A new object type is introduced, the sampler (representing generic sampling parameters).
 * The new sampler objects are represented by a new named type encapsulating the sampling parameters of a traditional texture object. Sampler objects may
 * be bound to texture units to supplant the bound texture's sampling state. A single sampler may be bound to more than one texture unit simultaneously,
 * allowing different textures to be accessed with a single set of shared sampling parameters. Also, by binding different sampler objects to texture units
 * to which the same texture has been bound, the same texture image data may be sampled with different sampling parameters.</p>
 * 
 * <p>Promoted to core in {@link GL33 OpenGL 3.3}.</p>
 */
public final class ARBSamplerObjects {

	/** Accepted by the {@code value} parameter of the GetBooleanv, GetIntegerv, GetInteger64v, GetFloatv and GetDoublev functions. */
	public static final int GL_SAMPLER_BINDING = 0x8919;

	/** Function address. */
	@JavadocExclude
	public final long
		GenSamplers,
		DeleteSamplers,
		IsSampler,
		BindSampler,
		SamplerParameteri,
		SamplerParameterf,
		SamplerParameteriv,
		SamplerParameterfv,
		SamplerParameterIiv,
		SamplerParameterIuiv,
		GetSamplerParameteriv,
		GetSamplerParameterfv,
		GetSamplerParameterIiv,
		GetSamplerParameterIuiv;

	@JavadocExclude
	public ARBSamplerObjects(FunctionProvider provider) {
		GenSamplers = provider.getFunctionAddress("glGenSamplers");
		DeleteSamplers = provider.getFunctionAddress("glDeleteSamplers");
		IsSampler = provider.getFunctionAddress("glIsSampler");
		BindSampler = provider.getFunctionAddress("glBindSampler");
		SamplerParameteri = provider.getFunctionAddress("glSamplerParameteri");
		SamplerParameterf = provider.getFunctionAddress("glSamplerParameterf");
		SamplerParameteriv = provider.getFunctionAddress("glSamplerParameteriv");
		SamplerParameterfv = provider.getFunctionAddress("glSamplerParameterfv");
		SamplerParameterIiv = provider.getFunctionAddress("glSamplerParameterIiv");
		SamplerParameterIuiv = provider.getFunctionAddress("glSamplerParameterIuiv");
		GetSamplerParameteriv = provider.getFunctionAddress("glGetSamplerParameteriv");
		GetSamplerParameterfv = provider.getFunctionAddress("glGetSamplerParameterfv");
		GetSamplerParameterIiv = provider.getFunctionAddress("glGetSamplerParameterIiv");
		GetSamplerParameterIuiv = provider.getFunctionAddress("glGetSamplerParameterIuiv");
	}

	// --- [ Function Addresses ] ---

	/** Returns the {@link org.lwjgl.opengl.ARBSamplerObjects} instance for the current context. */
	public static ARBSamplerObjects getInstance() {
		return GL.getCapabilities().__ARBSamplerObjects;
	}

	static ARBSamplerObjects create(java.util.Set<String> ext, FunctionProvider provider) {
		if ( !ext.contains("GL_ARB_sampler_objects") ) return null;

		ARBSamplerObjects funcs = new ARBSamplerObjects(provider);

		boolean supported = checkFunctions(
			funcs.GenSamplers, funcs.DeleteSamplers, funcs.IsSampler, funcs.BindSampler, funcs.SamplerParameteri, funcs.SamplerParameterf, 
			funcs.SamplerParameteriv, funcs.SamplerParameterfv, funcs.SamplerParameterIiv, funcs.SamplerParameterIuiv, funcs.GetSamplerParameteriv, 
			funcs.GetSamplerParameterfv, funcs.GetSamplerParameterIiv, funcs.GetSamplerParameterIuiv
		);

		return GL.checkExtension("GL_ARB_sampler_objects", funcs, supported);
	}

	// --- [ glGenSamplers ] ---

	/** Unsafe version of {@link #glGenSamplers GenSamplers} */
	@JavadocExclude
	public static void nglGenSamplers(int count, long samplers) {
		long __functionAddress = getInstance().GenSamplers;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglGenSamplers(count, samplers, __functionAddress);
	}

	/**
	 * Generates sampler object names.
	 *
	 * @param count    the number of sampler object names to generate
	 * @param samplers a buffer in which the generated sampler object names are stored
	 */
	public static void glGenSamplers(int count, ByteBuffer samplers) {
		if ( LWJGLUtil.CHECKS )
			checkBuffer(samplers, count << 2);
		nglGenSamplers(count, memAddress(samplers));
	}

	/** Alternative version of: {@link #glGenSamplers GenSamplers} */
	public static void glGenSamplers(IntBuffer samplers) {
		nglGenSamplers(samplers.remaining(), memAddress(samplers));
	}

	/** Single return value version of: {@link #glGenSamplers GenSamplers} */
	public static int glGenSamplers() {
		APIBuffer __buffer = apiBuffer();
		int samplers = __buffer.intParam();
		nglGenSamplers(1, __buffer.address() + samplers);
		return __buffer.intValue(samplers);
	}

	// --- [ glDeleteSamplers ] ---

	/** Unsafe version of {@link #glDeleteSamplers DeleteSamplers} */
	@JavadocExclude
	public static void nglDeleteSamplers(int count, long samplers) {
		long __functionAddress = getInstance().DeleteSamplers;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglDeleteSamplers(count, samplers, __functionAddress);
	}

	/**
	 * Deletes named sampler objects.
	 *
	 * @param count    the number of sampler objects to be deleted
	 * @param samplers an array of sampler objects to be deleted
	 */
	public static void glDeleteSamplers(int count, ByteBuffer samplers) {
		if ( LWJGLUtil.CHECKS )
			checkBuffer(samplers, count << 2);
		nglDeleteSamplers(count, memAddress(samplers));
	}

	/** Alternative version of: {@link #glDeleteSamplers DeleteSamplers} */
	public static void glDeleteSamplers(IntBuffer samplers) {
		nglDeleteSamplers(samplers.remaining(), memAddress(samplers));
	}

	/** Single value version of: {@link #glDeleteSamplers DeleteSamplers} */
	public static void glDeleteSamplers(int sampler) {
		APIBuffer __buffer = apiBuffer();
		int samplers = __buffer.intParam(sampler);
		nglDeleteSamplers(1, __buffer.address() + samplers);
	}

	// --- [ glIsSampler ] ---

	/**
	 * Determines if a name corresponds to a sampler object.
	 *
	 * @param sampler a value that may be the name of a sampler object
	 */
	public static boolean glIsSampler(int sampler) {
		long __functionAddress = getInstance().IsSampler;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		return GL33.nglIsSampler(sampler, __functionAddress);
	}

	// --- [ glBindSampler ] ---

	/**
	 * Binds a named sampler to a texturing target.
	 *
	 * @param unit    the index of the texture unit to which the sampler is bound
	 * @param sampler the name of a sampler
	 */
	public static void glBindSampler(int unit, int sampler) {
		long __functionAddress = getInstance().BindSampler;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglBindSampler(unit, sampler, __functionAddress);
	}

	// --- [ glSamplerParameteri ] ---

	/**
	 * Set the integer value of a sampler parameter.
	 *
	 * @param sampler the sampler object whose parameter to modify
	 * @param pname   the symbolic name of a single-valued sampler parameter. One of:<br>{@link GL11#GL_TEXTURE_WRAP_S TEXTURE_WRAP_S}, {@link GL11#GL_TEXTURE_WRAP_T TEXTURE_WRAP_T}, {@link GL12#GL_TEXTURE_WRAP_R TEXTURE_WRAP_R}, {@link GL11#GL_TEXTURE_MIN_FILTER TEXTURE_MIN_FILTER}, {@link GL11#GL_TEXTURE_MAG_FILTER TEXTURE_MAG_FILTER}, {@link GL12#GL_TEXTURE_MIN_LOD TEXTURE_MIN_LOD}, {@link GL12#GL_TEXTURE_MAX_LOD TEXTURE_MAX_LOD}, {@link GL14#GL_TEXTURE_LOD_BIAS TEXTURE_LOD_BIAS}, {@link GL14#GL_TEXTURE_COMPARE_MODE TEXTURE_COMPARE_MODE}, {@link GL14#GL_TEXTURE_COMPARE_FUNC TEXTURE_COMPARE_FUNC}
	 * @param param   the value of {@code pname}
	 */
	public static void glSamplerParameteri(int sampler, int pname, int param) {
		long __functionAddress = getInstance().SamplerParameteri;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglSamplerParameteri(sampler, pname, param, __functionAddress);
	}

	// --- [ glSamplerParameterf ] ---

	/**
	 * Float version of {@link #glSamplerParameteri SamplerParameteri}.
	 *
	 * @param sampler the sampler object whose parameter to modify
	 * @param pname   the symbolic name of a single-valued sampler parameter
	 * @param param   the value of {@code pname}
	 */
	public static void glSamplerParameterf(int sampler, int pname, float param) {
		long __functionAddress = getInstance().SamplerParameterf;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglSamplerParameterf(sampler, pname, param, __functionAddress);
	}

	// --- [ glSamplerParameteriv ] ---

	/** Unsafe version of {@link #glSamplerParameteri(int, int, java.nio.ByteBuffer) SamplerParameteri} */
	@JavadocExclude
	public static void nglSamplerParameteriv(int sampler, int pname, long params) {
		long __functionAddress = getInstance().SamplerParameteriv;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglSamplerParameteriv(sampler, pname, params, __functionAddress);
	}

	/**
	 * Pointer version of {@link #glSamplerParameteri SamplerParameteri}.
	 *
	 * @param sampler the sampler object whose parameter to modify
	 * @param pname   the symbolic name of a sampler parameter. One of:<br>{@link GL11#GL_TEXTURE_BORDER_COLOR TEXTURE_BORDER_COLOR}, {@link GL11#GL_TEXTURE_WRAP_S TEXTURE_WRAP_S}, {@link GL11#GL_TEXTURE_WRAP_T TEXTURE_WRAP_T}, {@link GL12#GL_TEXTURE_WRAP_R TEXTURE_WRAP_R}, {@link GL11#GL_TEXTURE_MIN_FILTER TEXTURE_MIN_FILTER}, {@link GL11#GL_TEXTURE_MAG_FILTER TEXTURE_MAG_FILTER}, {@link GL12#GL_TEXTURE_MIN_LOD TEXTURE_MIN_LOD}, {@link GL12#GL_TEXTURE_MAX_LOD TEXTURE_MAX_LOD}, {@link GL14#GL_TEXTURE_LOD_BIAS TEXTURE_LOD_BIAS}, {@link GL14#GL_TEXTURE_COMPARE_MODE TEXTURE_COMPARE_MODE}, {@link GL14#GL_TEXTURE_COMPARE_FUNC TEXTURE_COMPARE_FUNC}
	 * @param params  an array where the value or values of {@code pname} are stored
	 */
	public static void glSamplerParameteri(int sampler, int pname, ByteBuffer params) {
		nglSamplerParameteriv(sampler, pname, memAddress(params));
	}

	/** Alternative version of: {@link #glSamplerParameteri(int, int, java.nio.ByteBuffer) SamplerParameteri} */
	public static void glSamplerParameter(int sampler, int pname, IntBuffer params) {
		nglSamplerParameteriv(sampler, pname, memAddress(params));
	}

	// --- [ glSamplerParameterfv ] ---

	/** Unsafe version of {@link #glSamplerParameterf(int, int, java.nio.ByteBuffer) SamplerParameterf} */
	@JavadocExclude
	public static void nglSamplerParameterfv(int sampler, int pname, long params) {
		long __functionAddress = getInstance().SamplerParameterfv;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglSamplerParameterfv(sampler, pname, params, __functionAddress);
	}

	/**
	 * Float version of {@link #glSamplerParameteri SamplerParameteri}.
	 *
	 * @param sampler the sampler object whose parameter to modify
	 * @param pname   the symbolic name of a sampler parameter
	 * @param params  an array where the value or values of {@code pname} are stored
	 */
	public static void glSamplerParameterf(int sampler, int pname, ByteBuffer params) {
		nglSamplerParameterfv(sampler, pname, memAddress(params));
	}

	/** Alternative version of: {@link #glSamplerParameterf(int, int, java.nio.ByteBuffer) SamplerParameterf} */
	public static void glSamplerParameter(int sampler, int pname, FloatBuffer params) {
		nglSamplerParameterfv(sampler, pname, memAddress(params));
	}

	// --- [ glSamplerParameterIiv ] ---

	/** Unsafe version of {@link #glSamplerParameterIi(int, int, java.nio.ByteBuffer) SamplerParameterIi} */
	@JavadocExclude
	public static void nglSamplerParameterIiv(int sampler, int pname, long params) {
		long __functionAddress = getInstance().SamplerParameterIiv;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglSamplerParameterIiv(sampler, pname, params, __functionAddress);
	}

	/**
	 * Pure integer version of {@link #glSamplerParameteri SamplerParameteri}.
	 *
	 * @param sampler the sampler object whose parameter to modify
	 * @param pname   the symbolic name of a sampler parameter
	 * @param params  an array where the value or values of {@code pname} are stored
	 */
	public static void glSamplerParameterIi(int sampler, int pname, ByteBuffer params) {
		nglSamplerParameterIiv(sampler, pname, memAddress(params));
	}

	/** Alternative version of: {@link #glSamplerParameterIi(int, int, java.nio.ByteBuffer) SamplerParameterIi} */
	public static void glSamplerParameterI(int sampler, int pname, IntBuffer params) {
		nglSamplerParameterIiv(sampler, pname, memAddress(params));
	}

	// --- [ glSamplerParameterIuiv ] ---

	/** Unsafe version of {@link #glSamplerParameterIui(int, int, java.nio.ByteBuffer) SamplerParameterIui} */
	@JavadocExclude
	public static void nglSamplerParameterIuiv(int sampler, int pname, long params) {
		long __functionAddress = getInstance().SamplerParameterIuiv;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglSamplerParameterIuiv(sampler, pname, params, __functionAddress);
	}

	/**
	 * Unsigned pure integer version of {@link #glSamplerParameteri SamplerParameteri}.
	 *
	 * @param sampler the sampler object whose parameter to modify
	 * @param pname   the symbolic name of a sampler parameter
	 * @param params  an array where the value or values of {@code pname} are stored
	 */
	public static void glSamplerParameterIui(int sampler, int pname, ByteBuffer params) {
		nglSamplerParameterIuiv(sampler, pname, memAddress(params));
	}

	/** Alternative version of: {@link #glSamplerParameterIui(int, int, java.nio.ByteBuffer) SamplerParameterIui} */
	public static void glSamplerParameterIu(int sampler, int pname, IntBuffer params) {
		nglSamplerParameterIuiv(sampler, pname, memAddress(params));
	}

	// --- [ glGetSamplerParameteriv ] ---

	/** Unsafe version of {@link #glGetSamplerParameteri(int, int, java.nio.ByteBuffer) GetSamplerParameteri} */
	@JavadocExclude
	public static void nglGetSamplerParameteriv(int sampler, int pname, long params) {
		long __functionAddress = getInstance().GetSamplerParameteriv;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglGetSamplerParameteriv(sampler, pname, params, __functionAddress);
	}

	/**
	 * Return the integer value(s) of a sampler parameter.
	 *
	 * @param sampler the name of the sampler object from which to retrieve parameters
	 * @param pname   the symbolic name of a sampler parameter. One of:<br>{@link GL11#GL_TEXTURE_WRAP_S TEXTURE_WRAP_S}, {@link GL11#GL_TEXTURE_WRAP_T TEXTURE_WRAP_T}, {@link GL12#GL_TEXTURE_WRAP_R TEXTURE_WRAP_R}, {@link GL11#GL_TEXTURE_MIN_FILTER TEXTURE_MIN_FILTER}, {@link GL11#GL_TEXTURE_MAG_FILTER TEXTURE_MAG_FILTER}, {@link GL12#GL_TEXTURE_MIN_LOD TEXTURE_MIN_LOD}, {@link GL12#GL_TEXTURE_MAX_LOD TEXTURE_MAX_LOD}, {@link GL14#GL_TEXTURE_LOD_BIAS TEXTURE_LOD_BIAS}, {@link GL14#GL_TEXTURE_COMPARE_MODE TEXTURE_COMPARE_MODE}, {@link GL14#GL_TEXTURE_COMPARE_FUNC TEXTURE_COMPARE_FUNC}, ,, {@link GL11#GL_TEXTURE_BORDER_COLOR TEXTURE_BORDER_COLOR}
	 * @param params  the sampler parameters
	 */
	public static void glGetSamplerParameteri(int sampler, int pname, ByteBuffer params) {
		if ( LWJGLUtil.CHECKS )
			checkBuffer(params, 1 << 2);
		nglGetSamplerParameteriv(sampler, pname, memAddress(params));
	}

	/** Alternative version of: {@link #glGetSamplerParameteri(int, int, java.nio.ByteBuffer) GetSamplerParameteri} */
	public static void glGetSamplerParameter(int sampler, int pname, IntBuffer params) {
		if ( LWJGLUtil.CHECKS )
			checkBuffer(params, 1);
		nglGetSamplerParameteriv(sampler, pname, memAddress(params));
	}

	/** Single return value version of: {@link #glGetSamplerParameteri(int, int, java.nio.ByteBuffer) GetSamplerParameteri} */
	public static int glGetSamplerParameteri(int sampler, int pname) {
		APIBuffer __buffer = apiBuffer();
		int params = __buffer.intParam();
		nglGetSamplerParameteriv(sampler, pname, __buffer.address() + params);
		return __buffer.intValue(params);
	}

	// --- [ glGetSamplerParameterfv ] ---

	/** Unsafe version of {@link #glGetSamplerParameterf(int, int, java.nio.ByteBuffer) GetSamplerParameterf} */
	@JavadocExclude
	public static void nglGetSamplerParameterfv(int sampler, int pname, long params) {
		long __functionAddress = getInstance().GetSamplerParameterfv;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglGetSamplerParameterfv(sampler, pname, params, __functionAddress);
	}

	/**
	 * Float version of {@link #glGetSamplerParameteri GetSamplerParameteri}.
	 *
	 * @param sampler the name of the sampler object from which to retrieve parameters
	 * @param pname   the symbolic name of a sampler parameter
	 * @param params  the sampler parameters
	 */
	public static void glGetSamplerParameterf(int sampler, int pname, ByteBuffer params) {
		if ( LWJGLUtil.CHECKS )
			checkBuffer(params, 1 << 2);
		nglGetSamplerParameterfv(sampler, pname, memAddress(params));
	}

	/** Alternative version of: {@link #glGetSamplerParameterf(int, int, java.nio.ByteBuffer) GetSamplerParameterf} */
	public static void glGetSamplerParameter(int sampler, int pname, FloatBuffer params) {
		if ( LWJGLUtil.CHECKS )
			checkBuffer(params, 1);
		nglGetSamplerParameterfv(sampler, pname, memAddress(params));
	}

	/** Single return value version of: {@link #glGetSamplerParameterf(int, int, java.nio.ByteBuffer) GetSamplerParameterf} */
	public static float glGetSamplerParameterf(int sampler, int pname) {
		APIBuffer __buffer = apiBuffer();
		int params = __buffer.floatParam();
		nglGetSamplerParameterfv(sampler, pname, __buffer.address() + params);
		return __buffer.floatValue(params);
	}

	// --- [ glGetSamplerParameterIiv ] ---

	/** Unsafe version of {@link #glGetSamplerParameterIi(int, int, java.nio.ByteBuffer) GetSamplerParameterIi} */
	@JavadocExclude
	public static void nglGetSamplerParameterIiv(int sampler, int pname, long params) {
		long __functionAddress = getInstance().GetSamplerParameterIiv;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglGetSamplerParameterIiv(sampler, pname, params, __functionAddress);
	}

	/**
	 * Pure integer version of {@link #glGetSamplerParameteri GetSamplerParameteri}.
	 *
	 * @param sampler the name of the sampler object from which to retrieve parameters
	 * @param pname   the symbolic name of a sampler parameter
	 * @param params  the sampler parameters
	 */
	public static void glGetSamplerParameterIi(int sampler, int pname, ByteBuffer params) {
		if ( LWJGLUtil.CHECKS )
			checkBuffer(params, 1 << 2);
		nglGetSamplerParameterIiv(sampler, pname, memAddress(params));
	}

	/** Alternative version of: {@link #glGetSamplerParameterIi(int, int, java.nio.ByteBuffer) GetSamplerParameterIi} */
	public static void glGetSamplerParameterI(int sampler, int pname, IntBuffer params) {
		if ( LWJGLUtil.CHECKS )
			checkBuffer(params, 1);
		nglGetSamplerParameterIiv(sampler, pname, memAddress(params));
	}

	/** Single return value version of: {@link #glGetSamplerParameterIi(int, int, java.nio.ByteBuffer) GetSamplerParameterIi} */
	public static int glGetSamplerParameterIi(int sampler, int pname) {
		APIBuffer __buffer = apiBuffer();
		int params = __buffer.intParam();
		nglGetSamplerParameterIiv(sampler, pname, __buffer.address() + params);
		return __buffer.intValue(params);
	}

	// --- [ glGetSamplerParameterIuiv ] ---

	/** Unsafe version of {@link #glGetSamplerParameterIui(int, int, java.nio.ByteBuffer) GetSamplerParameterIui} */
	@JavadocExclude
	public static void nglGetSamplerParameterIuiv(int sampler, int pname, long params) {
		long __functionAddress = getInstance().GetSamplerParameterIuiv;
		if ( LWJGLUtil.CHECKS )
			checkFunctionAddress(__functionAddress);
		GL33.nglGetSamplerParameterIuiv(sampler, pname, params, __functionAddress);
	}

	/**
	 * Unsigned pure integer version of {@link #glGetSamplerParameteri GetSamplerParameteri}.
	 *
	 * @param sampler the name of the sampler object from which to retrieve parameters
	 * @param pname   the symbolic name of a sampler parameter
	 * @param params  the sampler parameters
	 */
	public static void glGetSamplerParameterIui(int sampler, int pname, ByteBuffer params) {
		if ( LWJGLUtil.CHECKS )
			checkBuffer(params, 1 << 2);
		nglGetSamplerParameterIuiv(sampler, pname, memAddress(params));
	}

	/** Alternative version of: {@link #glGetSamplerParameterIui(int, int, java.nio.ByteBuffer) GetSamplerParameterIui} */
	public static void glGetSamplerParameterIu(int sampler, int pname, IntBuffer params) {
		if ( LWJGLUtil.CHECKS )
			checkBuffer(params, 1);
		nglGetSamplerParameterIuiv(sampler, pname, memAddress(params));
	}

	/** Single return value version of: {@link #glGetSamplerParameterIui(int, int, java.nio.ByteBuffer) GetSamplerParameterIui} */
	public static int glGetSamplerParameterIui(int sampler, int pname) {
		APIBuffer __buffer = apiBuffer();
		int params = __buffer.intParam();
		nglGetSamplerParameterIuiv(sampler, pname, __buffer.address() + params);
		return __buffer.intValue(params);
	}

}