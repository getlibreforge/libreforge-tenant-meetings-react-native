import 'reflect-metadata';
import { injectable } from 'inversify';
import { AbstractAction, ActionExecutionContext } from '@libreforge/libreforge-framework';
import { Alert } from 'react-native';

@injectable()
export class SimpleAlertAction extends AbstractAction {

  name = 'SimpleAlert';

  getName() {
    return this.name;
  }

  async execute(context: ActionExecutionContext): Promise<{ next: boolean, result: any }> {
    
    console.warn('SimpleAlertAction called');
    Alert.alert('It is a Simple Alert');

    return { next: true, result: undefined };
  }

  override getArgsDefinition(): { name: string; type: string; label: string }[] {
    return [
    ];
  };  
}
