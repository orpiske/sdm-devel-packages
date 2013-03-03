
import net.orpiske.ssps.common.repository.utils.InstallDirUtils;
import net.orpiske.sdm.common.WorkdirUtils;
import net.orpiske.sdm.lib.io.IOUtil;
import net.orpiske.sdm.packages.BinaryPackage;

import static net.orpiske.sdm.lib.OsUtils.*;
import static net.orpiske.sdm.lib.io.IOUtil.*;
import static net.orpiske.sdm.lib.Executable.*;

import static net.orpiske.sdm.lib.Core.*;

class ApacheMaven extends BinaryPackage {
	def version = "10.9.1.0"
	def name = "apache-derby"
	
	def url = "http://apache.mirror.pop-sc.rnp.br/apache/db/derby/db-derby-10.9.1.0/db-derby-${version}-bin.tar.gz"
	
	void install(String artifactName) {
		String workdir = WorkdirUtils.getWorkDir()
		
		performInstall("${workdir}/db-derby-${version}-bin", "${name}", "${version}")
		
		println "Installing ${workdir}/db-derby-${version}-bin to ${installdir}"
		
		if (isNix()) {
			println "Creating symlinks"
			exec("/bin/ln", "-sf ${installdir}/${name}-${version} ${installdir}/${name}")
		}
	}	

	
	void uninstall() {
		if (isNix()) {
			println "Removing symlinks"
			exec("/bin/unlink", "${installdir}/${name}")
		}
	}
	
	
}