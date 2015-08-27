<%--

    Copyright (c) 2012-2015 Codenvy, S.A.
    All rights reserved. This program and the accompanying materials
    are made available under the terms of the Eclipse Public License v1.0
    which accompanies this distribution, and is available at
    http://www.eclipse.org/legal/epl-v10.html

    Contributors:
      Codenvy, S.A. - initial API and implementation

--%>
<!--
    CODENVY CONFIDENTIAL
    __________________

    [2012] - [2014] Codenvy, S.A.
    All Rights Reserved.

    NOTICE:  All information contained herein is, and remains
    the property of Codenvy S.A. and its suppliers,
    if any.  The intellectual and technical concepts contained
    herein are proprietary to Codenvy S.A.
    and its suppliers and may be covered by U.S. and Foreign Patents,
    patents in process, and are protected by trade secret or copyright law.
    Dissemination of this information or reproduction of this material
    is strictly forbidden unless prior written permission is obtained
    from Codenvy S.A..
-->

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Minimal Che</title>
    <link rel="shortcut icon" href="/_app/favicon.ico"/>
    <link href="http://fonts.googleapis.com/css?family=Droid+Sans+Mono" rel="stylesheet" type="text/css" />

    <script type="text/javascript" language="javascript">

        /**This parameter is needed to define sdk mode.*/
        window.sdk = 1;

        /**
         * Base IDE object
         */

        window.IDE = {};

        /**
         * Initial configuration
         */

        window.IDE.config = {

            "context": "${pageContext.request.contextPath}",

            "restContext": "${pageContext.request.contextPath}/api",

            "workspaceName": "<%= pageContext.getServletContext().getInitParameter("ws-name") %>",

            "workspaceId": "<%= pageContext.getServletContext().getInitParameter("ws-id") %>",

            "projectName": window.location.pathname.split("/")[3] ? window.location.pathname.split("/")[3] : null,

            "startupParams": location.search ? location.search.substring(1) : null,

            "hiddenFiles": ".*",

            "javaCodeAssistant": "${pageContext.request.contextPath}/api"

        };

        /**
         * Event handlers
         */

        window.IDE.eventHandlers = {};

        window.IDE.eventHandlers.initializationFailed = function(message) {
            if (message) {
                var err = new Error(message);
                window.alert(err.stack);
            } else {
                window.alert("Unable to initialize IDE");
            }
        };

        /**
         * Make URL pretty
         */

        window.history.pushState("", window.document.title, window.IDE.config.context + "/" + window.IDE.config.workspaceName);

    </script>

    <script type="text/javascript" language="javascript" src="_app/browserNotSupported.js"></script>
    <script type="text/javascript" language="javascript" src="_app/_app.nocache.js"></script>
</head>
</html>
