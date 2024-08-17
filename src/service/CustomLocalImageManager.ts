import { LocalImageManager } from "@libreforge/libreforge-framework-react-native";

export class CustomLocalImageManager extends LocalImageManager {

    private imageOccupied = require('../images/occupied.png');
    private imageVacant = require('../images/vacant.png');
    private labelSettings = require('../images/icons/settings.png');
    private labelVacant = require('../images/icons/label_vacant.png');
    private labelOccupied = require('../images/icons/label_occupied.png');

    getImageSource = (uri: string) => {
        if (uri.indexOf('settings') !== -1) {
            return this.labelSettings;
        } else if (uri.indexOf('label_vacant') !== -1) {
            return this.labelVacant;          
        } else if (uri.indexOf('label_occupied') !== -1) {
            return this.labelOccupied;          
        } else if (uri.indexOf('vacant') !== -1) {
            return this.imageVacant;
        } else if (uri.indexOf('occupied') !== -1) {
            return this.imageOccupied;
        } else {
            return { uri }
        }
    }
}