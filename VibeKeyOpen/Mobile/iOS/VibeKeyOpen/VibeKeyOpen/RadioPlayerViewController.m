//
//  PlayerViewController.m
//  VibeKeyOpen
//
//  Created by Jonathan Jungck on 3/31/15.
//  Copyright (c) 2015 Jonathan Jungck. All rights reserved.
//

#import "RadioPlayerViewController.h"
#import <AVKit/AVKit.h>
#import <AudioToolbox/AudioToolbox.h>
#import <AVFoundation/AVFoundation.h>


@interface RadioPlayerViewController () {
    AVAudioPlayer * audioPlayer;
    __weak IBOutlet UIButton *downvote;
    __weak IBOutlet UIButton *upvote;
}

@end

@implementation RadioPlayerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self tabBarController].tabBar.translucent = NO;
}

- (void) viewDidAppear:(BOOL)animated {
    NSString *soundFilePath = [[NSBundle mainBundle] pathForResource:@"PayNoMind"
ofType:@"mp3"];
    NSURL *fileURL = [[NSURL alloc] initFileURLWithPath: soundFilePath];
    audioPlayer = [[AVAudioPlayer alloc] initWithContentsOfURL:fileURL error:nil];
    [[AVAudioSession sharedInstance] setCategory:AVAudioSessionCategoryPlayback error:nil];
    [[AVAudioSession sharedInstance] setActive: YES error: nil];
    [[UIApplication sharedApplication] beginReceivingRemoteControlEvents];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)buttonPressed:(UIButton *)sender {
    if (sender.tag == 0) {
        [sender setImage:[UIImage imageNamed:@"downvote.png"] forState:UIControlStateNormal];
        [upvote setImage:[UIImage imageNamed:@"thumbs_up.png"] forState:UIControlStateNormal];
    }
    else if(sender.tag == 1) {
        [sender setImage:[UIImage imageNamed:@"pause_button.png"] forState:UIControlStateNormal];
        sender.tag = 3;
//        FSAudioController * audio = [[FSAudioController alloc] init];
//        [audio playFromURL:[[NSURL alloc] initWithString:@"http://wmhd-test.csse.rose-hulman.edu:8000/radio"]];
        //[audioPlayer play];
    }
    else if (sender.tag == 2) {
        [sender setImage:[UIImage imageNamed:@"upvote.png"] forState:UIControlStateNormal];
        [downvote setImage:[UIImage imageNamed:@"thumbs_down.png"] forState:UIControlStateNormal];
    }
    else if(sender.tag == 3) {
        [sender setImage:[UIImage imageNamed:@"play_button.png"] forState:UIControlStateNormal];
        sender.tag = 1;
        [audioPlayer pause];
    }
    
}

- (IBAction)volumeChanged:(UISlider *)sender {
    [audioPlayer setVolume:sender.value];
}


/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
