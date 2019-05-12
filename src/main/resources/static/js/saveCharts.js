$('#downloadPdf').click(function (event) {
    // get size of report page
    var reportPageHeight = 1800;
    var reportPageWidth = 2200;

    // create a new canvas object that we will populate with all other canvas objects
    var pdfCanvas = $('<canvas />').attr({
        id: "canvaspdf",
        width: reportPageWidth,
        height: reportPageHeight
    });
    var pdfctx = $(pdfCanvas)[0].getContext('2d');
    var pdfctxX = 0;
    var pdfctxY = 0;
    var buffer = 50;
    pdfctx.beginPath();
    pdfctx.rect(pdfctxX, pdfctxY, reportPageWidth, reportPageHeight);
    pdfctx.fillStyle = "white";
    pdfctx.fill();
    $("canvas").each(function (index) {
        var canvas = $(this);
        var canvasHeight = canvas.innerHeight();
        var canvasWidth = canvas.innerWidth();
        pdfctx.drawImage(canvas[0], pdfctxX, pdfctxY, canvasWidth, canvasHeight);
        pdfctxX += canvasWidth + buffer;

        if (index % 2 === 1) {
            pdfctxX = 0;
            pdfctxY += canvasHeight + buffer;
        }
    });

    // create new pdf and add our new canvas as an image
    var pdf = new jsPDF('l', 'pt', [reportPageWidth, reportPageHeight]);
    pdf.addImage($(pdfCanvas)[0], 'PNG', 0, 0);
    pdf.save();
});