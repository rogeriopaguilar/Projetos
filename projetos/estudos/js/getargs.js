
/*
This function returns the value from queryString in an object that acts as an map, where the
properties names are the index of objects and the values are stored in these indexes. If the property is defined
but has no value, the defaultValue will be used as the value for the property or null will be placed as the value if defaultValue is not
provided. If the parameter url is not provided, the url will be location.search, otherwise the url parameter will be used.
This parameter was introduced mainly for test purposes.

Ex:
var obj = WebUtil.getArgs('https://mail.google.com/mail/?shva=1#inbox&tn=10&testDefaultProperty=&testDefaultPropertyDois', 'defaultValue');

for(x in obj) {
    alert(x + ' = ' + obj[x]);
}

obj = WebUtil.getArgs(); //will use location.search as url parameter
for(x in obj) {
    alert(x + ' = ' + obj[x]);
}

obj = WebUtil.getArgs('defaultValue'); //will use location.search as url parameter
for(x in obj) {
    alert(x + ' = ' + obj[x]);
}


*/

var WebUtil = {

    config: {debug: true},

    debug: function (msg) 
    {
        if(config.debug === true) alert(msg);
    },

    getArgs: function (url, defaultValue) 
    {

        var args = new Object();
        debug('defaultValue = ' + defaultValue + "\n" + "url =" + url); 
    
        var query =  null;
        if( url ) {
            var pos = url.indexOf('?');
            if (pos != -1) {
                query =  url.substring(pos + 1); 
            } else {
                query = '';
            }
        } else {
            query =  location.search.substring(1); //removes the ? character
        }
        debug('query = ' + query); 
        
        var pairs = query.split('&');
        
        for(var i = 0; i < pairs.length; i++)
        {
            var pos = pairs[i].indexOf('=');
            var value = null;
            var name = null;
            if(pos == -1) 
            {
                name = pairs[i];
                value = defaultValue;
            } else 
            {
                name = pairs[i].substring(0, pos);
                value = decodeURIComponent( pairs[i].substring(pos + 1) );
            }
            
            args[name] = value ? value : defaultValue ? defaultValue : null;
        }
        
        return args;

    }
}


obj = WebUtil.getArgs();
var msgD = '\n********************************\n';

for(x in obj) {
    msgD += '\n' + x + ' = ' + obj[x] + '\n';
}

msgD += '\n********************************\n';


var obj = WebUtil.getArgs('https://mail.google.com/mail/?shva=1#inbox&tn=10&testDefaultProperty=&testDefaultPropertyDois', 'defaultValue');
msgD = '\n********************************\n';

for(x in obj) {
    msgD += '\n' + x + ' = ' + obj[x];
}

msgD += '\n********************************\n';

WebUtil.debug(msgD);


var obj = WebUtil.getArgs(location.search + '&testDefaultProperty=&testDefaultPropertyDois', 'defaultValue');
msgD = '\n********************************\n';

for(x in obj) {
    msgD += '\n' + x + ' = ' + obj[x];
}

msgD += '\n********************************\n';

WebUtil.debug(msgD);



