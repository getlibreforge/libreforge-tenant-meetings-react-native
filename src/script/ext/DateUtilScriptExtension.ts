import 'reflect-metadata';
import { injectable } from 'inversify';
import { AbstractScriptExtension } from '@libreforge/libreforge-framework';

export const SYMBOL_SCRIPT_EXTENSION = 'AbstractScriptExtension';

@injectable()
export class DateUtilScriptExtension extends AbstractScriptExtension {

  name = "DateUtil"


  getName() {
    return this.name;
  };

  isNowBetween(startDateTime: string, endDateTime: string): boolean {
    const now = new Date();
    const start = new Date(startDateTime);
    const end = new Date(endDateTime);

    return now >= start && now <= end;
  }
}
