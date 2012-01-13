	var detect = navigator.userAgent.toLowerCase();
	var windowsIE = (checkPlatform("msie") && checkPlatform("win"));
	var launchTID;
	function launchApp() {
		if (windowsIE) {
			document.write("<OBJECT CODEBASE=http://java.sun.com/update/1.6.0/jinstall-6-windows-i586.cab CLASSID=clsid:5852F5ED-8BF4-11D4-A245-0080C6F74284 HEIGHT=0 WIDTH=0>");
			document.write("<PARAM NAME=app VALUE=http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/dnsec.jnlp>");
			document.write("<PARAM NAME=back VALUE=false>");
			document.write("</OBJECT>");
		} else {
			/*if (navigator.mimeTypes && navigator.mimeTypes.length) {    
				if (!webstartVersionCheck("1.5")) {
					// Java Web Start not installed; open browser window to install site
					window.open("http://jdl.sun.com/webapps/getjava/BrowserRedirect?locale=en&host=java.com", "needdownload");
				}
				// Spin quietly, waiting to launch the app from the original window
				launchTID = setInterval('launchJNLP("dnsec.jnlp")', 100);
			}*/
			location.href = 'indexdireto.jsp'; 
		}
	}
	
	function webstartVersionCheck(versionString) {
		navigator.plugins.refresh(true);
		// First, determine if Webstart is available
		if (navigator.mimeTypes['application/x-java-jnlp-file']) {
			// Next, check for appropriate version family
			for (var i = 0; i < navigator.mimeTypes.length; ++i) {
				pluginType = navigator.mimeTypes[i].type;
				if (pluginType == "application/x-java-applet;version=" + versionString) {
					return true;
				}
			}
		}
	}

	function launchJNLP(app) {
		if (webstartVersionCheck("1.5")) {
			clearInterval(launchTID);
			window.location = app;
		}
	}

	function checkPlatform(string) {
		place = detect.indexOf(string) + 1;
		thestring = string;
		return place;
	}
