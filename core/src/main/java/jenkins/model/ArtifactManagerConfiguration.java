/*
 * The MIT License
 *
 * Copyright 2013 Jesse Glick.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package jenkins.model;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.util.DescribableList;
import java.io.IOException;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

/**
 * List of configured {@link ArtifactManagerFactory}s.
 * @since TODO
 */
@Extension
public class ArtifactManagerConfiguration extends GlobalConfiguration {
    
    public static ArtifactManagerConfiguration get() {
        return Jenkins.getInstance().getInjector().getInstance(ArtifactManagerConfiguration.class);
    }

    private final DescribableList<ArtifactManagerFactory,Descriptor<ArtifactManagerFactory>> artifactManagerFactories = new DescribableList<ArtifactManagerFactory,Descriptor<ArtifactManagerFactory>>(this);

    public ArtifactManagerConfiguration() {
        load();
    }

    private Object readResolve() {
        artifactManagerFactories.setOwner(this);
        return this;
    }

    public DescribableList<ArtifactManagerFactory, Descriptor<ArtifactManagerFactory>> getArtifactManagerFactories() {
        return artifactManagerFactories;
    }

    @Override public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
        try {
            artifactManagerFactories.rebuildHetero(req, json, ArtifactManagerFactory.all(), "artifactManagerFactories");
            return true;
        } catch (IOException x) {
            throw new FormException(x, "artifactManagerFactories");
        }
    }

}
