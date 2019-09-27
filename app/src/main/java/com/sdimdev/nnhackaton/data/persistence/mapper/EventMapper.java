package com.sdimdev.nnhackaton.data.persistence.mapper;

public class EventMapper {
	/*public ExceptionalSituationCode fromDbEntity(EventsDict eventsDict) {
		if (eventsDict == null || eventsDict.getGroupId()==null)
			return null;
		return new ExceptionalSituationCode(eventsDict.getTitle(), eventsDict.getEventCode(), ExceptionalGroup.from(eventsDict.getGroupId()));
	}

	public ExceptionalSituation fromDb(OrderEvent orderEvent) {
		return new ExceptionalSituation(fromDbEntity(orderEvent.getEvent()), orderEvent.getId(), orderEvent.getDateTime(), orderEvent.getCourierIdFull());
	}

	public Event toDb(ExceptionalSituation exceptionalSituation, String waybillnumber) {
		Event event = new Event();
		event.setCourierIdFull(exceptionalSituation.getCourierIdFull());
		event.setDateTime(exceptionalSituation.getDateTime());
		event.setEventCode(exceptionalSituation.getExceptionalSituationCode().getCode());
		event.setId(exceptionalSituation.getId());
		event.setWaybillNumber(waybillnumber);
		return event;
	}*/
}
