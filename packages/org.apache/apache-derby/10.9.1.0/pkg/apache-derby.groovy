
import net.orpiske.ssps.common.repository.utils.InstallDirUtils;
import net.orpiske.sdm.common.WorkdirUtils;
import net.orpiske.sdm.lib.io.IOUtil;
import net.orpiske.sdm.packages.BinaryPackage;

import static net.orpiske.sdm.lib.net.Downloader.*;
import static net.orpiske.sdm.lib.Unpack.*;
import static net.orpiske.sdm.lib.OsUtils.*;
import static net.orpiske.sdm.lib.io.IOUtil.*;
import static net.orpiske.sdm.lib.Executable.*;

class ApacheMaven extends BinaryPackage {
	def version = "10.9.1.0"
	def name = "apache-derby"
	
	def url = "http://apache.mirror.pop-sc.rnp.br/apache/db/derby/db-derby-10.9.1.0/db-derby-${version}-bin.tar.gz"

	void fetch(String url) {
		super.fetch(url)
	
		
		println "Fetching ${url}"
		download(url)		
	}
	
	
	void extract(String artifactName) {
		unpack(artifactName)
	}
	
	
	void install(String artifactName) {
		String installdir = InstallDirUtils.getInstallDir()
		String workdir = WorkdirUtils.getWorkDir()
		
		IOUtil.copy("${workdir}/db-derby-${version}", "${installdir}/${name}-${version}");
		
		println "Installing ${workdir}/db-derby-${version} to ${installdir}"
		
		if (isNix()) {
			println "Creating symlinks"
			exec("/bin/ln", "-sf ${installdir}/${name}-${version} ${installdir}/${name}")
		}
	}	

	
	
}