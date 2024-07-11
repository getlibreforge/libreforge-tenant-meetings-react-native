import 'reflect-metadata';
import { injectable } from 'inversify';
import { AbstractScriptExtension } from '@libreforge/libreforge-framework';
import RNSecureStorage, { ACCESSIBLE } from 'rn-secure-storage';

export const SYMBOL_SCRIPT_EXTENSION = 'AbstractScriptExtension';

@injectable()
export class SecureStorageScriptExtension extends AbstractScriptExtension {

  name = "SecureStorage"


  getName() {
    return this.name;
  };

  async read(key: string) {
    try {
      return await RNSecureStorage.getItem(key);
    } catch (error) {
      return undefined;
    }
  }
  
  async write(key: string, value: string) {
    await RNSecureStorage.setItem(key, value, {accessible: ACCESSIBLE.WHEN_UNLOCKED});
    return undefined;
  }  
}
