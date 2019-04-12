package Mixin.impl;

import java.io.File;
import java.io.IOException;
import net.minecraft.client.resources.IResource;

public interface IFallbackResourceManager {

	 IResource getResource(File location) throws IOException;
}
