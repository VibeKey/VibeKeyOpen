$(document).ready(function() {

    $("#weekSchedule").airtimeWeekSchedule({
        sourceDomain:"https://dj.wmhdradio.org/",
        dowText:{ sunday:"Sunday",
                monday:"Monday",
                tuesday:"Tuesday",
                wednesday:"Wednesday",
                thursday:"Thursday",
                friday:"Friday",
                saturday:"Saturday"},
        miscText:{time:"Time", programName:"Show Title", details:"Details", readMore:"More..."},
        updatePeriod: 300 //seconds
    });

    var d = new Date().getDay();
    $('#weekSchedule').tabs({selected: d === 0 ? 6 : d-1,
                            fx: {opacity: 'toggle'}});

});
