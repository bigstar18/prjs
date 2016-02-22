/**
    Project: CheckTree jQuery Plugin
    Version: 0.22
    Project Website: http://static.geewax.org/checktree/
    Author: JJ Geewax <jj@geewax.org>
    
    License:
        The CheckTree jQuery plugin is currently available for use in all personal or 
        commercial projects under both MIT and GPL licenses. This means that you can choose 
        the license that best suits your project, and use it accordingly.
*/
(function(jQuery) {
jQuery.fn.checkTree = function(settings) {
    
    settings = jQuery.extend({
        /* Callbacks
            The callbacks should be functions that take one argument. The checkbox tree
            will return the jQuery wrapped LI element of the item that was checked/expanded.
        */
        onExpand: null,
        onCollapse: null,
        onCheck: null,
        onUnCheck: null,
        onHalfCheck: null,
        onLabelHoverOver: null,
        onLabelHoverOut: null,
        
        /* Valid choices: 'expand', 'check' */
        labelAction: "expand",
        
        // Debug (currently does nothing)
        debug: false
    }, settings);
    
    var $tree = this;
    
    $tree.find("li")
        
        // Hide all checkbox inputs
        .each(function() {
            
            // Go through and hide only ul's (subtrees) that do not have a sibling div.expanded:
            // We do this to not collapse *all* the subtrees (if one is open and checkTree is called again)
            jQuery(this).find("ul").each(function() {
                if (!jQuery(this).siblings(".expanded").length) jQuery(this).hide();
            });
            
            // Copy the label
            var $label = jQuery(this).children("label").clone();
            // Create or the image for the checkbox next to the label
            var $checkbox = jQuery("<div class=\"radio\"></div>");
            // Create the image for the arrow (to expand and collapse the hidden trees)
            var $arrow = jQuery("<div class=\"arrow\"></div>");
            
            // If the li has children:
            if (jQuery(this).is(":has(ul)")) {
                // If the subtree is not visible, make the arrow collapsed. Otherwise expanded.
                if (jQuery(this).children("ul").is(":hidden")) $arrow.addClass("collapsed");
                else $arrow.addClass("expanded");
                
                // When you click the image, toggle the child list
                $arrow.click(function() {
                    jQuery(this).siblings("ul").toggle();
                    
                    // Swap the classes: expanded <-> collapsed and fire the onExpand/onCollapse events
                    if (jQuery(this).hasClass("collapsed")) {
                        jQuery(this)
                            .addClass("expanded")
                            .removeClass("collapsed")
                        ;
                        if (settings.onExpand) settings.onExpand(jQuery(this).parent());
                    }
                    else {
                        jQuery(this)
                            .addClass("collapsed")
                            .removeClass("expanded")
                        ;
                        if (settings.onCollapse) settings.onCollapse(jQuery(this).parent());
                    }
                });
            }
            
            // When you click the checkbox, it should do the checking/unchecking
            // Add the appropriate classes to the new checkbox image based on the old one:
            // Remove any existing arrows or checkboxes or labels
            jQuery(this).children(".arrow").remove();
            jQuery(this).children("label").remove();
            
            // Prepend the new arrow, label, and checkbox images to the front of the LI
            jQuery(this)
                .prepend($label)
                .prepend($arrow)
            ;
        })
        
        .find("label")
            // Clicking the labels should do the labelAction (either expand or check)
            .click(function() {
                var action = settings.labelAction;
                switch(settings.labelAction) {
                    case "expand":
                        jQuery(this).siblings(".arrow").click();
                        break;
                    case "check":
                        break;
                }
            })
            
            // Add a hover class to the labels when hovering
            .hover(
                function() { 
                    jQuery(this).addClass("hover");
                    if (settings.onLabelHoverOver) settings.onLabelHoverOver(jQuery(this).parent());
                },
                function() {
                    jQuery(this).removeClass("hover");
                    if (settings.onLabelHoverOut) settings.onLabelHoverOut(jQuery(this).parent());
                }
            )
        .end()
    ;

    return $tree;
};
})(jQuery);