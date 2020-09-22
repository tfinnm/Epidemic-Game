package prefabs;

import entities.Grant;
import entities.Job;

public class standardBlockGenerator {

	public standardBlockGenerator(int x, int y) {
		if (Math.random() < 0.25) {
			//residential
			for (int i = 0; i < 100; i += 20) {
				for (int j = 0; j < 100; j += 20) {
					new Apartment(x+i,y+j,"apartment");
				}
			}
		} else if (Math.random() < 0.25) {
			//offices/work places

			int gap = (int) (Math.random()*21);
			int i = (int) (Math.random()*(gap+1));

			new SmallOffice(x,y+i, "Office");
			new SmallOffice(x+50,y+i, "Office");
			new SmallOffice(x,y+40+gap, "Office");
			new SmallOffice(x+50,y+40+gap, "Office");
		} else {
			//commercial/entertainment (plus hospitals and FIRE/EMS)
			if (Math.random() < 0.2) {
				new commercialPharmacy(x,y,"CVS");
				new BasicShop(x,y+50,"General's Store");
			} else if (Math.random() < 0.2) {
				new SmallCafe(x,y,"Panera");
			} else if (Math.random() < 0.2) {
				if (Math.random() < 0.5) {
					new FireStation(x+50,y,3);
					new firstaid(x+80,y+80, "FireStation");
					new Apartment(x+60,y+80,"Fire Station");
				} else {
					new FireStation(x+50,y,2);
					new urgentCare(x+50,y+75, "Urgent Care");
				}
				if (Math.random() < 0.3) {
					new Theater(x,y, "Movie Theater");
				} else if (Math.random() < 0.3) {
					int gap = (int) (Math.random()*21);
					int i = (int) (Math.random()*(gap+1));
					new SmallOffice(x,y+i, "Office");
					new SmallOffice(x,y+40+gap, "Office");
				}
			} else if (Math.random() < 0.2) {
				//Hospital
				if (Math.random() < 0.6) {
					new EMSStation(x+50,y,2);
					new ward(x+50,y+40,"Hospital");
					new ward(x+50,y+60,"Hospital");
					new ICU(x+50,y+80,"Hospital");
					new urgentCare(x,y+75,"Emergency Room");
					new HospitalPharmacy(x+20,y+55, "Hospital");
					new SmallDoctorsOffice(x,y+55,"Hospital");
					new SmallDoctorsOffice(x,y+65,"Hospital");
					new SmallDoctorsOffice(x+10,y+5,"Hospital");
					new SmallDoctorsOffice(x+30,y+5,"Hospital");
					new TraumaRoom(x+10,y+15,"Hospital");
					new SurgeryRoom(x+10,y+35,"Hospital");
				} else {
					new Grant("New Hospital", "Builds a new hospital for $1m", true) {

						public boolean completeConditions() {
							return Job.bankBalance > 1000000;
						}

						public boolean applyConditions() {
							return Job.bankBalance > 1000000;
						}

						@Override
						public boolean isAvailible() {
							return true;
						}

						@Override
						public void onReward() {
							Job.changeBalance(-1000000);
							new EMSStation(x+50,y,2);
							new ward(x+50,y+40,"Hospital");
							new ward(x+50,y+60,"Hospital");
							new ICU(x+50,y+80,"Hospital");
							new urgentCare(x,y+75,"Emergency Room");
							new HospitalPharmacy(x+20,y+55, "Hospital");
							new SmallDoctorsOffice(x,y+55,"Hospital");
							new SmallDoctorsOffice(x,y+65,"Hospital");
							new SmallDoctorsOffice(x+10,y+5,"Hospital");
							new SmallDoctorsOffice(x+30,y+5,"Hospital");
							new TraumaRoom(x+10,y+15,"Hospital");
							new SurgeryRoom(x+10,y+35,"Hospital");
						}
						
					};
				}
			} else {
				//
			}
		}
	}
}
