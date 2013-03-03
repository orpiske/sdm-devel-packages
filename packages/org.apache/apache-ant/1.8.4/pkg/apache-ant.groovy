
import net.orpiske.ssps.common.repository.utils.InstallDirUtils;
import net.orpiske.sdm.common.WorkdirUtils;
import net.orpiske.sdm.lib.io.IOUtil;
import net.orpiske.sdm.packages.BinaryPackage;

import static net.orpiske.sdm.lib.net.Downloader.*;
import static net.orpiske.sdm.lib.Unpack.*;
import static net.orpiske.sdm.lib.OsUtils.*;
import static net.orpiske.sdm.lib.Executable.*;

import static net.orpiske.sdm.lib.Core.*;

class ApacheMaven extends BinaryPackage {
	def version = "1.8.4"
	def name = "apache-ant"	
	def url = "http://ftp.unicamp.br/pub/apache/ant/binaries/${name}-${version}-bin.tar.gz"

	void install(String artifactName) {
		performInstall("${name}", "${version}")
		
		println "Installing ${workDir}/${name}-${version} to ${installDir}"
		
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