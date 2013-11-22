import net.orpiske.sdm.packages.SourcePackage
import net.orpiske.sdm.plugins.scm.SourceRepository
import net.orpiske.sdm.plugins.builders.ProjectBuilder;
import net.orpiske.sdm.plugins.builders.MavenBuilder;
import org.apache.commons.io.FileUtils;

import static net.orpiske.sdm.lib.OsUtils.*
import static net.orpiske.sdm.lib.Core.*;
import static net.orpiske.sdm.lib.Executable.*;

class SDMCommon extends SourcePackage {
    def version = "0.3.0-SNAPSHOT"
    def name = "sdm-common"
    def url = "https://github.com/orpiske/sdm-common.git"

	def dependencies = [ "net.orpiske/ssps-common":"(0.3.0-SNAPSHOT, 0.3.99)"]

    def File buildDir = new File("${workDir}/${name}-${version}-build")

    void fetch(final String url) {
        println "Checking out the code to " + buildDir.getPath();

        if (buildDir.exists()) {
            println "Cleaning up previously created directory"
            FileUtils.deleteDirectory(buildDir)
        }

        SourceRepository repository = new SourceRepository(null, null)
        repository.checkout(url, buildDir.getPath())
    }


    @Override
    void build() {
        ProjectBuilder builder = new MavenBuilder(buildDir)

		println "Setting local Maven repository to " + workDir + File.separator + "m2repo"
		builder.setDefine("maven.repo.local", workDir + File.separator + "m2repo")

        if (builder.install() == 0) {
            println "Project ${name} compiled successfully"
        }


    }

    void install() {
      // no-op
    }

    void uninstall() {
		// no-op
    }
}
