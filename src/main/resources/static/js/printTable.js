function printTable() {
    $('#data').dynatable({
        dataset: {
            records: this
        }
    });
}