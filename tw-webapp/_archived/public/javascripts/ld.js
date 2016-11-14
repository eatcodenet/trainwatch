var substringMatcher = function(strs) {
	return function findMatches(q, cb) {
		var matches, substringRegex;

		// an array that will be populated with substring matches
		matches = [];

		// regex used to determine if a string contains the substring `q`
		substrRegex = new RegExp(q, 'i');

		// iterate through the pool of strings and for any string that
		// contains the substring `q`, add it to the `matches` array
		$.each(strs, function(i, str) {
			if (substrRegex.test(str)) {
				matches.push(str);
			}
		});

		cb(matches);
	};
};

var states = [ 'Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
		'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii',
		'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky',
		'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan',
		'Minnesota', 'Mississippi', 'Missouri', 'Montana', 'Nebraska',
		'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York',
		'North Carolina', 'North Dakota', 'Ohio', 'Oklahoma', 'Oregon',
		'Pennsylvania', 'Rhode Island', 'South Carolina', 'South Dakota',
		'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington',
		'West Virginia', 'Wisconsin', 'Wyoming' ];

function initTA() {
	$('#ld-tt .typeahead').typeahead({
		hint : true,
		highlight : true,
		minLength : 1
	}, {
		name : 'states',
		source : substringMatcher(states),
		templates: {
		  empty: [
		    '<div class="empty-message">',
			   'unable to find any Best Picture winners that match the current query',
			 '</div>'
		  ].join('\n'),
		  suggestion: function(data){
		    return '<p><strong>' + data + '</strong></p>';
		  }
        }

	});
}

(function($) {
	$(function() {
		initTA();
		console.log("Live Departures Ready");

	}); // end of document ready
})(jQuery); // end of jQuery name space
