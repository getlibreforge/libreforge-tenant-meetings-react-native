import 'reflect-metadata';
import { injectable } from 'inversify';
import { AbstractScriptExtension } from '@libreforge/libreforge-framework';

export const SYMBOL_SCRIPT_EXTENSION = 'AbstractScriptExtension';

@injectable()
export class TenantScriptExtension extends AbstractScriptExtension {

  name = "TenantScript"

  private mainContainerUUID = "cb0ce3a1-c389-4d43-8460-de469eb31df5";
  private labelImageUUID = "99ca9c12-5846-4e99-9cd5-2610d8d7f315";
  private nextMeetingContainerUUID = "64cba937-adec-452a-91fb-be195d6af7ca";

  getName() {
    return this.name;
  };

  isNowBetween(startDateTime: string, endDateTime: string): boolean {
    const now = new Date();
    const start = new Date(startDateTime);
    const end = new Date(endDateTime);

    return now >= start && now <= end;
  }

  isNowBefore(startDateTime: string): boolean {
    const now = new Date();
    const start = new Date(startDateTime);

    return now < start;
  }  

  async setBusy(ctx: any) {
    await ctx.setPropValue(this.mainContainerUUID, "backgroundImage", "https://getlibreforge.github.io/demo_files/tablet_image_occupied.png"); 
    await ctx.setPropValue(this.labelImageUUID, "src", "label_occupied.png");
    await ctx.setPropValue(this.nextMeetingContainerUUID, "backgroundColor", "#A83434");
  }

  async setAvailable(ctx: any) {
    await ctx.setPropValue(this.mainContainerUUID, "backgroundImage", "https://getlibreforge.github.io/demo_files/tablet_image_vacant.png"); 
    await ctx.setPropValue(this.labelImageUUID, "src", "label_vacant.png"); 
    await ctx.setPropValue(this.nextMeetingContainerUUID, "backgroundColor", "#35A177");
  }

  async parseMeetingDetails(ctx: any, items: any[]): Promise<boolean> {
    let isBusy = false;

    await ctx.setValue("lastUpdatedAt", "Meeting Room");
    await ctx.setValue("currentMeetingSlot", undefined);
    await ctx.setValue("currentMeetingName", 'No Meetings Scheduled');

    await ctx.setValue("nextMeetingSlot", undefined);
    await ctx.setValue("nextMeetingName", undefined);
    await ctx.setValue("nextMeetingUser", undefined);

    for (let i=0; i<items.length; i++) {
      const meeting = items[i];

      const startDateTime = meeting.start.dateTime;
      const endDateTime = meeting.end.dateTime;
      isBusy = isBusy || this.isNowBetween(startDateTime, endDateTime);
      
      if (isBusy === true) {
        /* Set meeting details */
        const startTime = new Date(startDateTime).toLocaleTimeString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true });
        const endTime = new Date(endDateTime).toLocaleTimeString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true });

        await ctx.setValue("currentMeetingName", 'Undisclosed Meeting Name');
        await ctx.setValue("currentMeetingSlot", `${startTime} - ${endTime}`);

        break;
      }
    }

    for (let i=0; i<items.length; i++) {
      const meeting = items[i];
      const startDateTime = meeting.start.dateTime;
      const endDateTime = meeting.end.dateTime; 
      
      if (!this.isNowBefore(startDateTime)) {
        continue;
      } else {
        const nextStartTime = new Date(startDateTime).toLocaleTimeString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true });
        const nextEndTime = new Date(endDateTime).toLocaleTimeString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true });

        await ctx.setValue("nextMeetingSlot", `${nextStartTime} - ${nextEndTime}`);
        await ctx.setValue("nextMeetingName", 'Undisclosed Meeting Name');
        await ctx.setValue("nextMeetingUser", 'Undisclosed User');
        
        break;
      }
    }

    const lastUpdatedAt = new Date().toLocaleTimeString('en-US', { hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true });
    await ctx.setValue("lastUpdatedAt", `Last Updated at ${lastUpdatedAt}`);

    return isBusy;
  }
}
