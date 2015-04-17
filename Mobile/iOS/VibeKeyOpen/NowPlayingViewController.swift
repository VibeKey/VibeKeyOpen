//
//  NowPlayingViewController.swift
//  VibeKeyOpen
//
//  Created by Jonathan Jungck on 3/29/15.
//  Copyright (c) 2015 Jonathan Jungck. All rights reserved.
//

import UIKit

class NowPlayingViewController: UIViewController {
    @IBOutlet weak var PlayButton: UIButton!

    override func viewDidLoad() {
        super.viewDidLoad()
        self.tabBarController?.tabBar.translucent = false;
        
//        PlayButton.setImage(UIImage(named: "play_button.png"), forState: UIControlState.Normal)
//        PlayButton.setImage(UIImage(named: "pause_button.png"), forState: UIControlState.Highlighted)

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    @IBAction func buttonPressed(sender: UIButton) {
        if(sender.tag == 1) {
            sender.setImage(UIImage(named: "pause_button.png"), forState: UIControlState.Normal)
            sender.tag = 3
        }
        else if (sender.tag == 3) {
            sender.setImage(UIImage(named: "play_button.png"), forState: UIControlState.Normal)
            sender.tag = 1
        }
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
