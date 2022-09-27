/* global jQuery, Coral */
(function($, Coral) {
    "use strict";

    console.log(" --------CLIENTLIBS LOADED------- ");

    var registry = $(window).adaptTo("foundation-registry");

    registry.register("foundation.validation.validator", {
        selector: "[data-validation=geeks-firstname-validation]",
        validate: function(element) {
            let el = $(element);
            let pattern=/[0-9a-z]/;
            let value=el.val();
            if(pattern.test(value)){
               return "Please add only Upper Case Letters in First name";
            }
        }
    });

   
})(jQuery, Coral);