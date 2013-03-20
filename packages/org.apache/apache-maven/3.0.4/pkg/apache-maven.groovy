
import net.orpiske.sdm.packages.BinaryPackage;

import static net.orpiske.sdm.lib.OsUtils.*;
import static net.orpiske.sdm.lib.io.IOUtil.*;
import static net.orpiske.sdm.lib.Core.*;
import static net.orpiske.sdm.lib.Executable.*;

class ApacheMaven extends BinaryPackage {
	def version = "3.0.4"
	def name = "apache-maven"
	def url = "http://archive.apache.org/dist/maven/binaries/${name}-${version}-bin.tar.gz"

	void install() {
		shield("${installDir}/${name}-${version}/conf/settings.xml")

		performInstall("${name}", "${version}")

		if (isNix()) {
			println "Creating symlinks"
			exec("/bin/ln", "-sf ${installDir}/${name}-${version} ${installDir}/${name}")
		}
	}

	void uninstall() {
		if (isNix()) {
			println "Removing symlinks"
			exec("/bin/unlink", "${installDir}/${name}")
		}
	}
}