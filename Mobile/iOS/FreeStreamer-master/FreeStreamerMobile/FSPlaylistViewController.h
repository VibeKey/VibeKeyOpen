/*
 * This file is part of the FreeStreamer project,
 * (C)Copyright 2011-2015 Matias Muhonen <mmu@iki.fi>
 * See the file ''LICENSE'' for using the code.
 *
 * https://github.com/muhku/FreeStreamer
 */

#import <UIKit/UIKit.h>

@class FSPlayerViewController;
@class FSParseRssPodcastFeedRequest;
@class FSStreamConfiguration;

/**
 * The playlist view controller of the iOS example application.
 *
 * Uses a table view. The table view items are retrieved using the
 * FSParsePlaylistFeedRequest class.
 */
@interface FSPlaylistViewController : UIViewController<UITableViewDataSource,UITableViewDelegate,UIAlertViewDelegate> {
    FSParseRssPodcastFeedRequest *_request;
    FSStreamConfiguration *_configuration;
}

/**
 * The playlist items displayed in the table view.
 */
@property (nonatomic,strong) NSMutableArray *playlistItems;
/**
 * The user provided playlist items, which are presented in addition to the playlistItems.
 */
@property (nonatomic,strong) NSMutableArray *userPlaylistItems;
/**
 * Reference to a navigation controller.
 */
@property (nonatomic,strong) IBOutlet UINavigationController *navigationController;
/**
 * Reference to a player view controller.
 */
@property (nonatomic,strong) IBOutlet FSPlayerViewController *playerViewController;
/**
 * Reference to a table view.
 */
@property (nonatomic,strong) IBOutlet UITableView *tableView;
/**
 * Reference to the buffer size control.
 */
@property (nonatomic,strong) IBOutlet UISegmentedControl *bufferSizeControl;

/**
 * An IBAction to add a new playlist item.
 *
 * @param sender The sender of the action.
 */
- (IBAction)addPlaylistItem:(id)sender;

/**
 * An IBAction to select the buffer size.
 *
 * @param sender The sender of the action.
 */
- (IBAction)selectBufferSize:(id)sender;

@end