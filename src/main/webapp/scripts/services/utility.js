angular.module('UtilityServiceModule', []).factory('Utility', function() {
    var getType = function(o) {
        var _t;
        return ((_t = typeof(o)) == "object" ? o == null && "null" || Object.prototype.toString.call(o).slice(8, -1) : _t).toLowerCase();
    }

    var extend = function(destination, source) {
        for (var p in source) {
            if (getType(source[p]) == "array" || getType(source[p]) == "object") {
                destination[p] = getType(source[p]) == "array" ? [] : {};
                arguments.callee(destination[p], source[p]);
            } else {
                destination[p] = source[p];
            }
        }
    }

    //used for sorting in grid.
    var sortingRule = function(a, b) {
        a = Number(a);
        b = Number(b);
        if (a === b) {
            return 0;
        }
        if (a > b) {
            return 1;
        }
        if (a < b) {
            return -1;
        }
    };

    return {
        extend: function(destination, source) {
            return extend(destination, source);
        },
        sortingRule: function(a, b) {
            return sortingRule(a, b);
        }
    }
})
