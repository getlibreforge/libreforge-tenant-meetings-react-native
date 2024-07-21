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
  
  isNowPlusMinBetween(startDateTime: string, endDateTime: string, min: number): boolean {
    const now = new Date();
    now.setTime(now.getTime() + min * 60 * 1000);

    const start = new Date(startDateTime);
    const end = new Date(endDateTime);
    
    console.log(`Now - ${now}`);
    console.log(`Start - ${start}`);
    console.log(`End - ${end}`);

    console.log(`Now > Start - ${now > start }`);
    console.log(`Now < Start - ${now < start }`);
    console.log(`Now > End - ${now > end }`);
    console.log(`Now < End - ${now < end }`);

    return now >= start && now <= end;
  }   
}
