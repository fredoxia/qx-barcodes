jQuery.extend({
    excel: function(rowClass) {
        
        if (rowClass[0] != '.')
            rowClass = '.' + rowClass;

        $(rowClass).bind('keyup',onkeyup);
    }
});

var keys = { left: 37, up: 38, right: 39, down: 40};
function onkeyup(evt) {
	var focusType = $("input:focus").attr("type");
	
	if (focusType == undefined)
		return;
	
    var td = $(evt.target).closest('td');
    var tr = $(evt.currentTarget);
    var table = tr.parent();

    switch (evt.keyCode) {
        case keys.down:
            go("down", tr, td);
            break;
        case keys.up:
            go("up", tr, td);
            break;
        case keys.left:
            go("left", tr, td);
            break;
        case keys.right:
            go("right", tr, td);
            break;
    }
}

    function go(to, tr, td) {
        var toFocus = null;

        toFocus = findFocus(tr, td, to);

        if (toFocus) {
        	try {
                toFocus.select();
        	} catch (e){
        		toFocus.focus();
        	}
        }
    }

    function findFocus(tr, td, goTo){
        var toFocus = null;
        switch (goTo) {
        case 'left':
            toFocus = firstInput(td.prevAll('td'));

            if (!toFocus){
                tr = tr.prev('tr');
                while (toFocus == null && tr.length !=0){
                    toFocus = lastInput(tr.children('td'));

                    if (toFocus == null)
                        tr = tr.prev('tr');
                }
            }
            break;
        case 'right':
            toFocus = firstInput(td.nextAll('td'));
            if (!toFocus){
                tr = tr.next('tr');

                while (toFocus == null && tr.length !=0){
                    toFocus = firstInput(tr.children('td'));

                    if (toFocus == null)
                        tr = tr.next('tr');
                }
            }
            break;
        case 'up':
            toFocus = firstInput(tr.prev('tr').children('td'), td.prevAll('td').size());
            if (!toFocus){
                tr = tr.prev('tr');

                while (toFocus == null && tr.length !=0){
                    toFocus = firstInput(tr.children('td'), td.prevAll('td').size());

                    if (toFocus == null)
                        tr = tr.prev('tr');
                }
            }
            break;
        case 'down':
            toFocus = firstInput(tr.next('tr').children('td'), td.prevAll('td').size());
            if (!toFocus){
                tr = tr.next('tr');

                while (toFocus == null && tr.length !=0){
                    toFocus = firstInput(tr.children('td'), td.prevAll('td').size());

                    if (toFocus == null)
                        tr = tr.next('tr');
                }
            }
            break;
        }
        return toFocus;
    }


    function firstInput(tds, start) {
        if (!start)
            start = 0;
        for (var i = start; i < tds.size(); i++) {
            var inputs = $(tds[i]).children('input');
            if (inputs.size()){
                var input = inputs[0];

                if (input.disabled != true && input.readOnly != true)
                    return input;
            }
        }
        return null;
    }

    function lastInput(tds) {
        for (var i = tds.size() - 1; i >= 0; i--) {
            var inputs = $(tds[i]).children('input');
            if (inputs.size()){
                var input = inputs[0];

                if (input.disabled != true && input.readOnly != true)
                    return input;
            }
        }
        return null;
    }