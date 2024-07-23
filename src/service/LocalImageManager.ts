export class LocalImageManager {

    private imageOccupied = require('../images/occupied.png');
    private imageSoon = require('../images/soon.png');
    private imageVacant = require('../images/vacant.png');

    getImageSource = (uri: string) => {
        if (uri.indexOf('vacant') !== -1) {
            return this.imageVacant;
        } else if (uri.indexOf('soon') !== -1) {
            return this.imageSoon;
        } else if (uri.indexOf('occupied') !== -1) {
            return this.imageOccupied;
        } else {
            return { uri }
        }
    }
}