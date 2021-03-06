/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
package org.lwjgl.glfw;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.system.libffi.*;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.libffi.LibFFI.*;

/** Instances of this interface may be passed to the {@link GLFW#glfwSetScrollCallback} method. */
public abstract class GLFWScrollCallback extends Closure.Void {

	private static final ByteBuffer    CIF  = FFICIF.malloc();
	private static final PointerBuffer ARGS = BufferUtils.createPointerBuffer(3);

	static {
		ARGS.put(0, ffi_type_pointer);
		ARGS.put(1, ffi_type_double);
		ARGS.put(2, ffi_type_double);

		int status = ffi_prep_cif(CIF, CALL_CONVENTION_DEFAULT, ffi_type_void, ARGS);
		if ( status != FFI_OK )
			throw new IllegalStateException(String.format("Failed to prepare GLFWScrollCallback callback interface. Status: 0x%X", status));
	}

	protected GLFWScrollCallback() {
		super(CIF);
	}

	/**
	 * Will be called from a libffi closure invocation. Decodes the arguments and passes them to {@link #invoke}.
	 *
	 * @param args pointer to an array of jvalues
	 */
	@Override
	protected void callback(long args) {
		invoke(
			memGetAddress(memGetAddress(POINTER_SIZE * 0 + args)),
			memGetDouble(memGetAddress(POINTER_SIZE * 1 + args)),
			memGetDouble(memGetAddress(POINTER_SIZE * 2 + args))
		);
	}
	/**
	 * Will be called when a scrolling device is used, such as a mouse wheel or scrolling area of a touchpad.
	 *
	 * @param window  the window that received the event
	 * @param xoffset the scroll offset along the x-axis
	 * @param yoffset the scroll offset along the y-axis
	 */
	public abstract void invoke(long window, double xoffset, double yoffset);

	/** A functional interface for {@link org.lwjgl.glfw.GLFWScrollCallback}. */
	public interface SAM {
		void invoke(long window, double xoffset, double yoffset);
	}

}